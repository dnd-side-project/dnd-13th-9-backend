package com.example.dnd_13th_9_be.plan.persistence.adapter;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import com.example.dnd_13th_9_be.global.error.BusinessException;
import com.example.dnd_13th_9_be.plan.application.dto.PlanDetailResult;
import com.example.dnd_13th_9_be.plan.application.dto.PlanSummaryResult;
import com.example.dnd_13th_9_be.plan.application.port.PlanQueryPort;
import com.example.dnd_13th_9_be.plan.persistence.PlanRepository;
import com.example.dnd_13th_9_be.plan.persistence.entity.PlanEntity;

import static com.example.dnd_13th_9_be.global.error.ErrorCode.NOT_FOUND_PLAN;

@Component
@RequiredArgsConstructor
public class PlanQueryAdapter implements PlanQueryPort {
  private final PlanRepository planRepository;

  @Override
  public List<PlanSummaryResult> findSummariesByUserId(Long userId) {
    return planRepository.findSummariesByUserId(userId).stream()
        .map(
            r ->
                new PlanSummaryResult(
                    r.id(), r.name(), r.createdAt(), r.folderCount(), r.isDefault()))
        .toList();
  }

  @Override
  public long countByUserId(Long userId) {
    return planRepository.countByUserId(userId);
  }

  @Override
  public void verifyById(Long planId) {
    var isNotExist = !planRepository.existsById(planId);
    if (isNotExist) {
      throw new BusinessException(NOT_FOUND_PLAN);
    }
  }

  @Override
  public PlanDetailResult findById(Long planId) {
    PlanEntity entity =
        planRepository.findById(planId).orElseThrow(() -> new BusinessException(NOT_FOUND_PLAN));
    return new PlanDetailResult(
        entity.getId(), entity.getName(), entity.getCreatedAt(), entity.isDefault());
  }
}
