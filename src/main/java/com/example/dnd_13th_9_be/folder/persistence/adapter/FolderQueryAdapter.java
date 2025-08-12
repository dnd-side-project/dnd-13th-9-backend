package com.example.dnd_13th_9_be.folder.persistence.adapter;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import com.example.dnd_13th_9_be.folder.application.dto.FolderDetailResult;
import com.example.dnd_13th_9_be.folder.application.dto.FolderSummaryResult;
import com.example.dnd_13th_9_be.folder.application.port.FolderQueryPort;
import com.example.dnd_13th_9_be.folder.persistence.FolderEntity;
import com.example.dnd_13th_9_be.folder.persistence.FolderRepository;
import com.example.dnd_13th_9_be.global.error.BusinessException;

import static com.example.dnd_13th_9_be.global.error.ErrorCode.NOT_FOUND_FOLDER;

@Component
@RequiredArgsConstructor
public class FolderQueryAdapter implements FolderQueryPort {
  private final FolderRepository folderRepository;

  @Override
  public List<FolderSummaryResult> findSummariesByPlanId(Long planId) {
    return folderRepository.findSummariesByPlanId(planId).stream()
        .map(
            r ->
                new FolderSummaryResult(
                    r.id(),
                    r.name(),
                    r.createdAt(),
                    r.locationRecordCount() + r.propertyRecordCount(),
                    r.isDefault()))
        .toList();
  }

  @Override
  public long countByPlanId(Long planId) {
    return folderRepository.countByPlanId(planId);
  }

  @Override
  public void verifyById(Long folderId) {
    var isNotExist = !folderRepository.existsById(folderId);
    if (isNotExist) {
      throw new BusinessException(NOT_FOUND_FOLDER);
    }
  }

  @Override
  public FolderDetailResult findById(Long folderId) {
    FolderEntity entity =
        folderRepository
            .findById(folderId)
            .orElseThrow(() -> new BusinessException(NOT_FOUND_FOLDER));
    return new FolderDetailResult(
        entity.getId(), entity.getName(), entity.getCreatedAt(), entity.isDefault());
  }
}
