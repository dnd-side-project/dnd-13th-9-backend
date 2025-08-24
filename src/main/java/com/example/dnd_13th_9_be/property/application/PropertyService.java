package com.example.dnd_13th_9_be.property.application;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

import com.example.dnd_13th_9_be.checklist.application.model.ChecklistCategoryModel;
import com.example.dnd_13th_9_be.checklist.application.model.UserRequiredItemModel;
import com.example.dnd_13th_9_be.checklist.application.repository.ChecklistCategoryRepository;
import com.example.dnd_13th_9_be.checklist.application.repository.UserRequiredItemRepository;
import com.example.dnd_13th_9_be.checklist.presentation.dto.ChecklistResponse;
import com.example.dnd_13th_9_be.common.utils.S3Manager;
import com.example.dnd_13th_9_be.folder.application.repository.FolderRepository;
import com.example.dnd_13th_9_be.global.error.BusinessException;
import com.example.dnd_13th_9_be.global.error.S3ImageException;
import com.example.dnd_13th_9_be.property.application.dto.PropertyCategoryMemoDto;
import com.example.dnd_13th_9_be.property.application.dto.PropertyDto;
import com.example.dnd_13th_9_be.property.application.dto.PropertyImageDto;
import com.example.dnd_13th_9_be.property.application.repository.PropertyCategoryMemoRepository;
import com.example.dnd_13th_9_be.property.application.repository.PropertyImageRepository;
import com.example.dnd_13th_9_be.property.application.repository.PropertyRepository;
import com.example.dnd_13th_9_be.property.application.repository.PropertyRequiredCheckRepository;
import com.example.dnd_13th_9_be.property.persistence.dto.PropertyCategoryMemoResult;
import com.example.dnd_13th_9_be.property.persistence.dto.PropertyImageResult;
import com.example.dnd_13th_9_be.property.persistence.dto.PropertyResult;
import com.example.dnd_13th_9_be.property.presentation.dto.request.PropertyCategoryMemoRequest;
import com.example.dnd_13th_9_be.property.presentation.dto.request.UpsertPropertyRequest;
import com.example.dnd_13th_9_be.property.presentation.dto.response.PropertyDetailResponse;

import static com.example.dnd_13th_9_be.global.error.ErrorCode.PROPERTY_RECORD_IMAGE_LIMIT;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PropertyService {

  private final S3Manager s3Manager;
  private final FolderRepository folderRepository;
  private final PropertyRepository propertyRepository;
  private final PropertyImageRepository propertyImageRepository;
  private final PropertyRequiredCheckRepository propertyRequiredCheckRepository;
  private final ChecklistCategoryRepository checklistCategoryRepository;
  private final UserRequiredItemRepository userRequiredItemRepository;
  private final PropertyCategoryMemoRepository propertyCategoryMemoRepository;

  @Transactional(rollbackFor = {S3ImageException.class})
  public void createPropertyRecord(
      Long userId, List<MultipartFile> files, UpsertPropertyRequest request) {

    // 이미지 갯수 확인
    if (files != null && files.size() > 5) {
      throw new BusinessException(PROPERTY_RECORD_IMAGE_LIMIT);
    }

    // 유효한 folder 인지 확인
    folderRepository.verifyById(request.folderId());

    // property entity 저장
    PropertyDto propertyDto = PropertyDto.from(request);
    PropertyResult savedProperty = propertyRepository.save(propertyDto);

    // category memo 저장
    List<PropertyCategoryMemoRequest> filteredMemoList = request.getCategoryMemo();
    filteredMemoList.forEach(
        memo -> {
          checklistCategoryRepository.verifyById(memo.categoryId());
          propertyCategoryMemoRepository.save(
              PropertyCategoryMemoDto.from(savedProperty.propertyId(), memo));
        });

    // property_required_check 항목 저장
    List<UserRequiredItemModel> requiredItemModelList =
        userRequiredItemRepository.findAllByUserId(userId);
    requiredItemModelList.forEach(
        item -> {
          propertyRequiredCheckRepository.save(item.getItemId(), savedProperty.propertyId());
        });

    // property_image 항목 저장
    AtomicInteger imageOrder = new AtomicInteger(1);
    List<PropertyImageDto> images =
        Optional.ofNullable(files).orElseGet(Collections::emptyList).stream()
            .map(
                file ->
                    PropertyImageDto.builder()
                        .propertyId(savedProperty.propertyId())
                        .imageUrl(s3Manager.upload(file))
                        .order(imageOrder.getAndIncrement())
                        .build())
            .toList();
    images.forEach(propertyImageRepository::save);
  }

  @Transactional
  public void deleteProperty(Long userId, Long propertyId) {
    List<PropertyImageResult> images = propertyImageRepository.findAllByPropertyId(propertyId);
    images.forEach(i -> s3Manager.delete(i.imageUrl()));
    propertyRepository.delete(userId, propertyId);
  }

  public PropertyDetailResponse getProperty(Long propertyId) {
    // 폴더에 메모 갯수 10개 이상인지 체크

    // 매물 메모 조회
    PropertyResult property = propertyRepository.findById(propertyId);

    // 카테고리 메모 조회
    List<PropertyCategoryMemoResult> memoList =
        propertyCategoryMemoRepository.findAllByPropertyId(propertyId);

    // 매물 이미지 조회
    List<PropertyImageResult> imageList = propertyImageRepository.findAllByPropertyId(propertyId);

    // 카테고리/아이템 조회
    List<ChecklistCategoryModel> categories = checklistCategoryRepository.findAll();

    // 매물 메모 필수 확인 조회
    Set<Long> requiredIdSet = propertyRequiredCheckRepository.findAllIdsByPropertyId(propertyId);
    ChecklistResponse checklist = ChecklistResponse.of(categories, requiredIdSet);

    return PropertyDetailResponse.of(property, memoList, imageList, checklist);
  }

  @Transactional
  public void updateProperty(
      Long userId, Long propertyId, List<MultipartFile> files, UpsertPropertyRequest request) {
    // 매물 메모 업데이트
    PropertyDto propertyDto = PropertyDto.from(request);
    propertyRepository.update(propertyId, propertyDto);

    // 카테고리 메모 업데이트
    propertyCategoryMemoRepository.deleteAllByPropertyId(propertyId);
    request
        .getCategoryMemo()
        .forEach(
            memo -> {
              checklistCategoryRepository.verifyById(memo.categoryId());
              propertyCategoryMemoRepository.update(PropertyCategoryMemoDto.from(propertyId, memo));
            });

    // 기존 이미지 조회
    List<Long> deletedImageIdList =
        Optional.ofNullable(request.deletedImageIdList()).orElseGet(Collections::emptyList);
    List<PropertyImageResult> imageList = propertyImageRepository.findAllByPropertyId(propertyId);

    // 이미지 저장 갯수 초과 확인
    if (imageList.size() - deletedImageIdList.size() + files.size() > 5) {
      throw new BusinessException(PROPERTY_RECORD_IMAGE_LIMIT);
    }

    // 삭제된 이미지 id 확인 후 기존 이미지에서 제거
    deletedImageIdList.forEach(
        imageId -> {
          propertyImageRepository.verifyByIdAndPropertyId(imageId, propertyId);
          propertyImageRepository.delete(imageId, propertyId);
        });

    imageList = propertyImageRepository.findAllByPropertyId(propertyId);

    // 기존 이미지 재정렬
    AtomicInteger imageOrder = new AtomicInteger(1);
    imageList.forEach(
        image -> {
          propertyImageRepository.updateOrder(image.imageId(), imageOrder.getAndIncrement());
        });

    // 새로 추가된 이미지 추가
    List<PropertyImageDto> images =
        files.stream()
            .map(
                file ->
                    PropertyImageDto.builder()
                        .propertyId(propertyId)
                        .imageUrl(s3Manager.upload(file))
                        .order(imageOrder.getAndIncrement())
                        .build())
            .toList();
    images.forEach(propertyImageRepository::save);
  }
}
