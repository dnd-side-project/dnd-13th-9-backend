package com.example.dnd_13th_9_be.plan.persistence;

import java.util.List;
import jakarta.persistence.EntityManager;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import com.example.dnd_13th_9_be.global.error.BusinessException;
import com.example.dnd_13th_9_be.plan.application.dto.PlanDetailResult;
import com.example.dnd_13th_9_be.plan.application.dto.PlanSummaryResult;
import com.example.dnd_13th_9_be.plan.application.repository.PlanRepository;
import com.example.dnd_13th_9_be.plan.persistence.entity.Plan;
import com.example.dnd_13th_9_be.user.persistence.User;

import static com.example.dnd_13th_9_be.global.error.ErrorCode.NOT_FOUND_PLAN;

@Component
@RequiredArgsConstructor
public class PlanRepositoryImpl implements PlanRepository {
  private final EntityManager em;
  private final JpaPlanRepository planRepository;

  @Override
  public PlanDetailResult create(Long userId, String name, boolean isDefault) {
    User userRef = em.getReference(User.class, userId);
    Plan plan = Plan.of(userRef, name, isDefault);
    planRepository.save(plan);
    return new PlanDetailResult(
        plan.getId(), plan.getName(), plan.getCreatedAt().toLocalDateTime(), plan.getIsDefault());
  }

  @Override
  public boolean rename(Long userId, Long planId, String newName) {
    return planRepository.rename(userId, planId, newName);
  }

  @Override
  public boolean delete(Long userId, Long planId) {
    return planRepository.deleteByIdIfExists(userId, planId);
  }

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
  public void existsById(Long planId) {
    var isNotExist = !planRepository.existsById(planId);
    if (isNotExist) {
      throw new BusinessException(NOT_FOUND_PLAN);
    }
  }

  @Override
  public PlanDetailResult findById(Long planId) {
    Plan entity =
        planRepository.findById(planId).orElseThrow(() -> new BusinessException(NOT_FOUND_PLAN));
    return new PlanDetailResult(
        entity.getId(),
        entity.getName(),
        entity.getCreatedAt().toLocalDateTime(),
        entity.getIsDefault());
  }
}
