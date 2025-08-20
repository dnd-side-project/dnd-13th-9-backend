package com.example.dnd_13th_9_be.plan.application.repository;

import java.util.List;

import com.example.dnd_13th_9_be.plan.application.dto.PlanDetailResult;
import com.example.dnd_13th_9_be.plan.application.dto.PlanSummaryResult;

public interface PlanRepository {
  PlanDetailResult create(Long userId, String name, boolean isDefault);

  boolean rename(Long userId, Long planId, String newName);

  boolean delete(Long userId, Long planId);

  List<PlanSummaryResult> findSummariesByUserId(Long userId);

  long countByUserId(Long userId);

  void verifyExistsById(Long userId, Long planId);

  PlanDetailResult findById(Long planId);
}
