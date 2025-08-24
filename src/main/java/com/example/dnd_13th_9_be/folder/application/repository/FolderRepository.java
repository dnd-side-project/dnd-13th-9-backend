package com.example.dnd_13th_9_be.folder.application.repository;

import java.util.List;

import com.example.dnd_13th_9_be.folder.application.dto.FolderDetailResult;
import com.example.dnd_13th_9_be.folder.application.dto.FolderSummaryResult;

public interface FolderRepository {
  FolderDetailResult create(Long userId, Long planId, String name, boolean isDefault);

  boolean rename(Long userId, Long folderId, String newName);

  boolean delete(Long userId, Long folderId);

  List<FolderSummaryResult> findSummariesByPlanId(Long userId, Long planId);

  long countByPlanId(Long planId);

  void verifyById(Long userId, Long folderId);

  FolderDetailResult findByIdAndUserId(Long folderId, Long userId);

  long countFolderRecord(Long folderId);
}
