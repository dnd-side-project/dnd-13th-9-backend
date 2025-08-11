package com.example.dnd_13th_9_be.plan.application;

import java.util.List;

public interface PlanQueryPort {
  List<PlanSummaryResult> findSummariesByUserId(Long userId);
}
