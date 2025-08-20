package com.example.dnd_13th_9_be.plan.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.example.dnd_13th_9_be.global.error.BusinessException;
import com.example.dnd_13th_9_be.plan.application.dto.PlanDetailResult;
import com.example.dnd_13th_9_be.plan.application.dto.PlanSummaryResult;
import com.example.dnd_13th_9_be.plan.application.repository.PlanRepository;

import static com.example.dnd_13th_9_be.global.error.ErrorCode.DEFAULT_PLAN_CANNOT_BE_DELETE;
import static com.example.dnd_13th_9_be.global.error.ErrorCode.PLAN_CREATION_LIMIT;
import static com.example.dnd_13th_9_be.global.error.ErrorCode.PLAN_DELETE_FAILED;
import static com.example.dnd_13th_9_be.global.error.ErrorCode.PLAN_RENAME_FAILED;

@Service
@RequiredArgsConstructor
public class PlanService {
  private final PlanRepository planRepository;

  private static final String DEFAULT_PLAN_NAME = "기본 계획";

  @Transactional
  public PlanDetailResult createDefaultPlan(Long userId) {
    return planCommandPort.create(userId, DEFAULT_PLAN_NAME, true);
  }

  @Transactional(readOnly = true)
  public List<PlanSummaryResult> getPlanList(Long userId) {
    return planRepository.findSummariesByUserId(userId);
  }

  @Transactional
  public PlanDetailResult createPlan(Long userId, String name) {
    boolean isPlanLimitExceed = planRepository.countByUserId(userId) >= 10;
    if (isPlanLimitExceed) {
      throw new BusinessException(PLAN_CREATION_LIMIT);
    }

    return planRepository.create(userId, name, false);
  }

  @Transactional
  public void renamePlan(Long userId, Long planId, String name) {
    planRepository.verifyExistsById(userId, planId);
    boolean isFailRename = !planRepository.rename(userId, planId, name);
    if (isFailRename) {
      throw new BusinessException(PLAN_RENAME_FAILED);
    }
  }

  @Transactional
  public void deletePlan(Long userId, Long planId) {
    PlanDetailResult plan = planRepository.findByIdAndUserId(planId, userId);
    if (plan.isDefault()) {
      throw new BusinessException(DEFAULT_PLAN_CANNOT_BE_DELETE);
    }
    boolean isFailDelete = !planRepository.delete(userId, planId);
    if (isFailDelete) {
      throw new BusinessException(PLAN_DELETE_FAILED);
    }
  }
}
