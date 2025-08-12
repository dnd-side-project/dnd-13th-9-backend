package com.example.dnd_13th_9_be.folder.application;

import java.util.List;

import com.example.dnd_13th_9_be.folder.application.dto.FolderSummaryResult;

public interface FolderQueryPort {
  List<FolderSummaryResult> findSummariesByPlanId(Long planId);
}
