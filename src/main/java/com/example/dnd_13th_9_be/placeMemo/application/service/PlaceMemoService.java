package com.example.dnd_13th_9_be.placeMemo.application.service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

import com.example.dnd_13th_9_be.common.utils.S3Manager;
import com.example.dnd_13th_9_be.global.error.BusinessException;
import com.example.dnd_13th_9_be.global.error.ErrorCode;
import com.example.dnd_13th_9_be.placeMemo.application.dto.*;
import com.example.dnd_13th_9_be.placeMemo.application.model.PlaceMemoModel;
import com.example.dnd_13th_9_be.placeMemo.application.model.converter.PlaceMemoRequestConverter;
import com.example.dnd_13th_9_be.placeMemo.application.repository.PlaceMemoRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlaceMemoService {

  private final PlaceMemoRequestConverter placeMemoModelRequestConverter;
  private final S3Manager s3Manager;
  private final PlaceMemoRepository placeMemoRepository;

  @Transactional
  public CreatePlaceMemoResponse create(final Long userId, final CreatePlaceMemoRequest request) {

    List<String> urls =
        (request.images() == null ? List.<MultipartFile>of() : request.images())
            .stream().filter(f -> !f.isEmpty()).map(s3Manager::upload).toList();

    PlaceMemoModel model = placeMemoModelRequestConverter.from(request, urls);
    PlaceMemoModel savedModel = placeMemoRepository.save(userId, model);
    return new CreatePlaceMemoResponse(savedModel.getId());
  }

  @Transactional
  public QueryPlaceMemoResponse findOne(final Long placeMemoId, final Long userId) {
    PlaceMemoModel memoModel =
        placeMemoRepository
            .findByIdAndUserId(placeMemoId, userId)
            .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_PLACE_MEMO));
    return QueryPlaceMemoResponse.from(memoModel);
  }

  @Transactional
  public void delete(final Long placeMemoId, final Long userId) {
    PlaceMemoModel memoModel =
        placeMemoRepository
            .findByIdAndUserId(placeMemoId, userId)
            .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_PLACE_MEMO));

    if (memoModel.getImageUrls() != null && !memoModel.getImageUrls().isEmpty()) {
      s3Manager.deleteMultiple(memoModel.getImageUrls());
    }
    placeMemoRepository.deleteById(placeMemoId, userId);
  }

  @Transactional
  public void update(
      final Long placeMemoId, final Long userId, final UpdatePlaceMemoRequest request) {
    List<String> uploadedUrls = new ArrayList<>();
    try {
      if (request.newImages() != null) {
        for (MultipartFile file : request.newImages()) {
          if (file != null && !file.isEmpty()) {
            uploadedUrls.add(s3Manager.upload(file));
          }
        }
      }
    } catch (RuntimeException e) {
      if (!uploadedUrls.isEmpty()) {
        s3Manager.deleteMultiple(uploadedUrls);
      }
      throw e;
    }

    LinkedHashSet<String> finalSet = new LinkedHashSet<>();
    if (request.existingImageUrls() != null) {
      finalSet.addAll(request.existingImageUrls());
    }
    finalSet.addAll(uploadedUrls);

    if (finalSet.size() > 5) {
      if (!uploadedUrls.isEmpty()) {
        s3Manager.deleteMultiple(uploadedUrls); // 보상 삭제
      }
      throw new BusinessException(ErrorCode.INVALID_IMAGE_COUNT, "이미지는 최대 5개까지 업로드할 수 있습니다.");
    }

    PlaceMemoModel updatedModel =
        request.toModel(List.copyOf(finalSet)).toBuilder().id(placeMemoId).build();
    placeMemoRepository.update(userId, placeMemoId, updatedModel);
  }

  @Transactional
  public QueryPlaceMemoListResponse findAll(final Long folderId, final Long userId) {
    List<QueryPlaceMemoResponse> items =
        placeMemoRepository.findByFolderIdAndUserId(folderId, userId).stream()
            .map(QueryPlaceMemoResponse::from)
            .toList();
    return QueryPlaceMemoListResponse.of(items);
  }
}
