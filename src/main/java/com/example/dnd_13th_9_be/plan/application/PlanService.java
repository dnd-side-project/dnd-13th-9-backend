package com.example.dnd_13th_9_be.plan.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.example.dnd_13th_9_be.global.error.BusinessException;
import com.example.dnd_13th_9_be.plan.application.dto.PlanDetailResult;
import com.example.dnd_13th_9_be.plan.application.dto.PlanSummaryResult;

import static com.example.dnd_13th_9_be.global.error.ErrorCode.DEFAULT_PLAN_CANNOT_BE_DELETE;
import static com.example.dnd_13th_9_be.global.error.ErrorCode.NOT_FOUND_USER;
import static com.example.dnd_13th_9_be.global.error.ErrorCode.PLAN_CREATION_LIMIT;
import static com.example.dnd_13th_9_be.global.error.ErrorCode.PLAN_DELETE_FAILED;
import static com.example.dnd_13th_9_be.global.error.ErrorCode.PLAN_RENAME_FAILED;

@Service
@RequiredArgsConstructor
public class PlanService {
  private final PlanQueryPort planQueryPort;
  private final PlanCommandPort planCommandPort;
  private final UserAccessPort userAccessPort;

  @Transactional(readOnly = true)
  public List<PlanSummaryResult> getPlanList(Long userId) {
    return planQueryPort.findSummariesByUserId(userId);
  }

  @Transactional
  public PlanDetailResult createPlan(Long userId, String name) {
    var isNotExistsUser = !userAccessPort.existsById(userId);
    if (isNotExistsUser) {
      throw new BusinessException(NOT_FOUND_USER);
    }

    boolean isPlanLimitExceed = planQueryPort.countByUserId(userId) >= 10;
    if (isPlanLimitExceed) {
      throw new BusinessException(PLAN_CREATION_LIMIT);
    }

    return planCommandPort.create(userId, name, false);
  }

  @Transactional
  public void renamePlan(Long planId, String name) {
    planQueryPort.verifyById(planId);
    boolean isFailRename = !planCommandPort.rename(planId, name);
    if (isFailRename) {
      throw new BusinessException(PLAN_RENAME_FAILED);
    }
  }

  @Transactional
  public void deletePlan(Long planId) {
    PlanDetailResult plan = planQueryPort.findById(planId);
    if (plan.isDefault()) {
      throw new BusinessException(DEFAULT_PLAN_CANNOT_BE_DELETE);
    }
    boolean isFailDelete = !planCommandPort.delete(planId);
    if (isFailDelete) {
      throw new BusinessException(PLAN_DELETE_FAILED);
    }
  }
}
