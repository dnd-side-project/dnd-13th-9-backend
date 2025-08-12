package com.example.dnd_13th_9_be.plan.persistence.adapter;

import jakarta.persistence.EntityManager;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import com.example.dnd_13th_9_be.plan.application.dto.PlanDetailResult;
import com.example.dnd_13th_9_be.plan.application.port.PlanCommandPort;
import com.example.dnd_13th_9_be.plan.persistence.PlanRepository;
import com.example.dnd_13th_9_be.plan.persistence.entity.PlanEntity;
import com.example.dnd_13th_9_be.user.persistence.UserEntity;

@Component
@RequiredArgsConstructor
public class PlanCommandAdapter implements PlanCommandPort {
  private final EntityManager em;
  private final PlanRepository planRepository;

  @Override
  public PlanDetailResult create(Long userId, String name, boolean isDefault) {
    UserEntity userRef = em.getReference(UserEntity.class, userId);
    PlanEntity planEntity = PlanEntity.of(userRef, name, isDefault);
    planRepository.save(planEntity);
    return new PlanDetailResult(
        planEntity.getId(),
        planEntity.getName(),
        planEntity.getCreatedAt(),
        planEntity.getIsDefault());
  }

  @Override
  public boolean rename(Long planId, String newName) {
    return planRepository.rename(planId, newName);
  }

  @Override
  public boolean delete(Long planId) {
    return planRepository.deleteByIdIfExists(planId);
  }
}
