package com.example.dnd_13th_9_be.plan.application;

import java.util.List;

import com.example.dnd_13th_9_be.plan.application.dto.PlanSummaryResult;

public interface PlanQueryPort {
  List<PlanSummaryResult> findSummariesByUserId(Long userId);
}
