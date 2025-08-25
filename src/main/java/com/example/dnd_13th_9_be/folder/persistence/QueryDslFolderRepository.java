package com.example.dnd_13th_9_be.folder.persistence;

import com.example.dnd_13th_9_be.folder.application.dto.RecordSummaryResult;
import com.example.dnd_13th_9_be.folder.persistence.dto.RecordSummary;
import java.util.List;

import com.example.dnd_13th_9_be.folder.persistence.dto.FolderSummary;

public interface QueryDslFolderRepository {
  List<FolderSummary> findSummariesByPlanId(Long userId, Long planId);

  boolean rename(Long userId, Long folderId, String newName);

  boolean deleteByIdIfExists(Long userId, Long folderId);

  Long countByPlanId(Long planId);

  Long countFolderRecord(Long folderId);

  List<RecordSummary> findAllRecordByIdAndUserId(Long userId, Long folderId);
}
