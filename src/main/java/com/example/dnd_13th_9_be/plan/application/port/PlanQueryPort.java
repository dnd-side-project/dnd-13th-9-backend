package com.example.dnd_13th_9_be.plan.application.port;

import java.util.List;

import com.example.dnd_13th_9_be.plan.application.dto.PlanDetailResult;
import com.example.dnd_13th_9_be.plan.application.dto.PlanSummaryResult;

public interface PlanQueryPort {
  List<PlanSummaryResult> findSummariesByUserId(Long userId);

  long countByUserId(Long userId);

  void verifyById(Long planId);

  PlanDetailResult findById(Long planId);
}
