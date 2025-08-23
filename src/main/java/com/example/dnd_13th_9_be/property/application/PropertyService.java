package com.example.dnd_13th_9_be.property.application;

import static com.example.dnd_13th_9_be.global.error.ErrorCode.PROPERTY_RECORD_IMAGE_LIMIT;

import com.example.dnd_13th_9_be.checklist.application.model.ChecklistCategoryModel;
import com.example.dnd_13th_9_be.checklist.application.model.UserRequiredItemModel;
import com.example.dnd_13th_9_be.checklist.application.repository.ChecklistCategoryRepository;
import com.example.dnd_13th_9_be.checklist.presentation.dto.ChecklistResponse;
import com.example.dnd_13th_9_be.folder.application.repository.FolderRepository;
import com.example.dnd_13th_9_be.global.error.BusinessException;
import com.example.dnd_13th_9_be.property.application.dto.PropertyCategoryMemoDto;
import com.example.dnd_13th_9_be.property.application.dto.PropertyDto;
import com.example.dnd_13th_9_be.property.application.dto.PropertyImageDto;
import com.example.dnd_13th_9_be.property.application.repository.PropertyCategoryMemoRepository;
import com.example.dnd_13th_9_be.property.application.repository.PropertyImageRepository;
import com.example.dnd_13th_9_be.property.application.repository.PropertyRequiredCheckRepository;
import com.example.dnd_13th_9_be.property.persistence.dto.PropertyResult;
import com.example.dnd_13th_9_be.property.presentation.dto.request.PropertyCategoryMemoRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

import com.example.dnd_13th_9_be.checklist.application.repository.UserRequiredItemRepository;
import com.example.dnd_13th_9_be.common.utils.S3Manager;
import com.example.dnd_13th_9_be.global.error.S3ImageException;
import com.example.dnd_13th_9_be.property.application.model.PropertyImageModel;
import com.example.dnd_13th_9_be.property.application.model.PropertyModel;
import com.example.dnd_13th_9_be.property.application.model.PropertyRequiredCheckModel;
import com.example.dnd_13th_9_be.property.application.repository.PropertyRepository;
import com.example.dnd_13th_9_be.property.presentation.dto.request.UpsertPropertyRequest;
import com.example.dnd_13th_9_be.property.presentation.dto.response.PropertyDetailResponse;

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
      Long userId, List<MultipartFile> files, UpsertPropertyRequest request
  ) {

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
    filteredMemoList.forEach( memo -> {
      checklistCategoryRepository.verifyById(memo.categoryId());
      propertyCategoryMemoRepository.save(PropertyCategoryMemoDto.from(savedProperty.propertyId(), memo));
    });

    // property_required_check 항목 저장
    List<UserRequiredItemModel> requiredItemModelList = userRequiredItemRepository.findAllByUserId(userId);
    requiredItemModelList.forEach(item -> {
      propertyRequiredCheckRepository.save(item.getItemId(), savedProperty.propertyId());
    });

    // property_image 항목 저장
    AtomicInteger imageOrder = new AtomicInteger(1);
    List<PropertyImageDto> images =
        Optional.ofNullable(files)
            .orElseGet(Collections::emptyList)
            .stream()
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
    List<PropertyImageModel> images = propertyImageRepository.findAllByPropertyId(propertyId);
    images.forEach(i -> s3Manager.delete(i.imageUrl()));
    propertyRepository.delete(userId, propertyId);
  }

  public PropertyDetailResponse getProperty(Long propertyId) {
    PropertyModel property = propertyRepository.findDetailById(propertyId);
    List<ChecklistCategoryModel> categories = checklistCategoryRepository.findAll();
    Set<Long> requiredIds = property.requiredChecklist().stream().map(
        PropertyRequiredCheckModel::checklistId
    ).collect(Collectors.toSet());
    ChecklistResponse checklist = ChecklistResponse.of(categories, requiredIds);
    return PropertyDetailResponse.of(property, checklist);
  }

  @Transactional
  public PropertyModel updateProperty(Long userId, List<MultipartFile> files, UpsertPropertyRequest request) {
    // 기존 이미지 조회
    List<PropertyImageModel> imageModelList = propertyImageRepository.findAllByPropertyId(
        request.propertyId());

    Set<Long> deletedImageIdList = new HashSet<>(Optional.ofNullable(request.deletedImageIdList())
        .orElseGet(Collections::emptyList));
    if (imageModelList.size() - deletedImageIdList.size() + files.size() > 5) {
      throw new BusinessException(PROPERTY_RECORD_IMAGE_LIMIT);
    }

    List<PropertyImageModel> savedImageModelList = new ArrayList<>();
    imageModelList.forEach(imageModel -> {
      if (deletedImageIdList.contains(imageModel.propertyImageId())) {
        s3Manager.delete(imageModel.imageUrl());
        propertyImageRepository.delete(imageModel.propertyImageId());
      } else {
        savedImageModelList.add(imageModel);
      }
    });

    AtomicInteger imageOrder = new AtomicInteger(1);
    savedImageModelList.forEach(imageModel -> {
      propertyImageRepository.updateOrder(imageModel.propertyImageId(), imageOrder.getAndIncrement());
    });

    List<PropertyImageModel> newImageModel =
        Optional.of(files).orElseGet(Collections::emptyList).stream()
            .map(
                file ->
                    PropertyImageModel.builder()
                        .imageUrl(s3Manager.upload(file))
                        .order(imageOrder.getAndIncrement())
                        .build())
            .toList();

    // 저장된 checklist 목록
    List<PropertyRequiredCheckModel> requiredCheckModelList =
        userRequiredItemRepository.findAllByUserId(userId).stream()
            .map(m -> PropertyRequiredCheckModel.builder().checklistId(m.getItemId()).build())
            .toList();

    return propertyRepository.save(PropertyModel.from(newImageModel, request, requiredCheckModelList));
  }
}
