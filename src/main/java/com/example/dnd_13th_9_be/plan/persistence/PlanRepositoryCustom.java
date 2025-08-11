package com.example.dnd_13th_9_be.plan.persistence;

import java.util.List;

import com.example.dnd_13th_9_be.plan.persistence.dto.PlanSummary;

public interface PlanRepositoryCustom {
  List<PlanSummary> findSummariesByUserId(Long userId);
}
