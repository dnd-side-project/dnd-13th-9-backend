package com.example.dnd_13th_9_be.folder.persistence;

import java.util.List;

import com.example.dnd_13th_9_be.folder.persistence.dto.FolderSummary;

public interface FolderRepositoryCustom {
  List<FolderSummary> findSummariesByPlanId(Long planId);
  boolean rename(Long folderId, String newName);
  boolean delete(Long folderId);
  Long countByPlanId(Long planId);
}
