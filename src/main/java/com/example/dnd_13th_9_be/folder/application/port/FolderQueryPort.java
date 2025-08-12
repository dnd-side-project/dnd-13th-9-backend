package com.example.dnd_13th_9_be.folder.application.port;

import java.util.List;

import com.example.dnd_13th_9_be.folder.application.dto.FolderDetailResult;
import com.example.dnd_13th_9_be.folder.application.dto.FolderSummaryResult;

public interface FolderQueryPort {
  List<FolderSummaryResult> findSummariesByPlanId(Long planId);

  long countByPlanId(Long planId);

  void verifyById(Long folderId);

  FolderDetailResult findById(Long folderId);
}
