package com.example.dnd_13th_9_be.plan.persistence;

import java.util.List;

import com.example.dnd_13th_9_be.plan.persistence.dto.PlanSummary;

public interface QueryDslPlanRepository {
  List<PlanSummary> findSummariesByUserId(Long userId);

  boolean rename(Long userId, Long planId, String newName);

  boolean deleteByIdIfExists(Long userId, Long planId);

  Long countByUserId(Long userId);

  boolean verifyExistsById(Long userId, Long planId);
}
