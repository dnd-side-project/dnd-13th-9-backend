package com.example.dnd_13th_9_be.folder.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.example.dnd_13th_9_be.folder.application.dto.FolderDetailResult;
import com.example.dnd_13th_9_be.folder.application.dto.FolderSummaryResult;
import com.example.dnd_13th_9_be.folder.application.port.FolderCommandPort;
import com.example.dnd_13th_9_be.folder.application.port.FolderQueryPort;
import com.example.dnd_13th_9_be.folder.application.port.PlanAccessPort;
import com.example.dnd_13th_9_be.global.error.BusinessException;

import static com.example.dnd_13th_9_be.global.error.ErrorCode.DEFAULT_FOLDER_CANNOT_BE_DELETE;
import static com.example.dnd_13th_9_be.global.error.ErrorCode.FOLDER_CREATION_LIMIT;
import static com.example.dnd_13th_9_be.global.error.ErrorCode.FOLDER_DELETE_FAILED;
import static com.example.dnd_13th_9_be.global.error.ErrorCode.FOLDER_RENAME_FAILED;
import static com.example.dnd_13th_9_be.global.error.ErrorCode.NOT_FOUND_PLAN;

@Service
@RequiredArgsConstructor
public class FolderService {
  private final FolderQueryPort folderQueryPort;
  private final FolderCommandPort folderCommandPort;
  private final PlanAccessPort planAccessPort;

  private static final String DEFAULT_FOLDER_NAME = "기본 폴더";

  @Transactional
  public void createDefaultFolder(Long planId) {
    folderCommandPort.create(planId, DEFAULT_FOLDER_NAME, true);
  }

  @Transactional(readOnly = true)
  public List<FolderSummaryResult> getFolderList(Long planId) {
    verifyPlanExists(planId);
    return folderQueryPort.findSummariesByPlanId(planId);
  }

  @Transactional
  public FolderDetailResult createFolder(Long planId, String name) {
    verifyPlanExists(planId);

    boolean isFolderLimitExceed = folderQueryPort.countByPlanId(planId) >= 10;
    if (isFolderLimitExceed) {
      throw new BusinessException(FOLDER_CREATION_LIMIT);
    }

    return folderCommandPort.create(planId, name, false);
  }

  @Transactional
  public void renameFolder(Long folderId, String name) {
    folderQueryPort.verifyById(folderId);
    boolean isFailRename = !folderCommandPort.rename(folderId, name);
    if (isFailRename) {
      throw new BusinessException(FOLDER_RENAME_FAILED);
    }
  }

  @Transactional
  public void deleteFolder(Long folderId) {
    FolderDetailResult folder = folderQueryPort.findById(folderId);
    if (folder.isDefault()) {
      throw new BusinessException(DEFAULT_FOLDER_CANNOT_BE_DELETE);
    }

    boolean isFailDelete = !folderCommandPort.delete(folderId);
    if (isFailDelete) {
      throw new BusinessException(FOLDER_DELETE_FAILED);
    }
  }

  private void verifyPlanExists(Long planId) {
    if (!planAccessPort.existsById(planId)) {
      throw new BusinessException(NOT_FOUND_PLAN);
    }
  }
}
