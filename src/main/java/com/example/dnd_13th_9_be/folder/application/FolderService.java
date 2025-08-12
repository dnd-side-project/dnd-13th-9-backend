package com.example.dnd_13th_9_be.folder.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.example.dnd_13th_9_be.folder.application.dto.FolderSummaryResult;
import com.example.dnd_13th_9_be.folder.application.port.PlanAccessPort;
import com.example.dnd_13th_9_be.global.error.BusinessException;

import static com.example.dnd_13th_9_be.global.error.ErrorCode.NOT_FOUND_PLAN;

@Service
@RequiredArgsConstructor
public class FolderService {
  private final FolderQueryPort folderQueryPort;
  private final PlanAccessPort planAccessPort;

  @Transactional(readOnly = true)
  public List<FolderSummaryResult> getFolderList(Long planId) {
    var isNotExistPlan = !planAccessPort.existById(planId);
    if (isNotExistPlan) {
      throw new BusinessException(NOT_FOUND_PLAN);
    }
    return folderQueryPort.findSummariesByPlanId(planId);
  }
}
