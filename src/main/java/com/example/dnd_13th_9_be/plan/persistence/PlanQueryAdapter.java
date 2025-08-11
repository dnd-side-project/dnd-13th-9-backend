package com.example.dnd_13th_9_be.plan.persistence;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import com.example.dnd_13th_9_be.plan.application.PlanQueryPort;
import com.example.dnd_13th_9_be.plan.application.PlanSummaryResult;

@Component
@RequiredArgsConstructor
public class PlanQueryAdapter implements PlanQueryPort {
  private final PlanRepository planRepository;

  @Override
  public List<PlanSummaryResult> findSummariesByUserId(Long userId) {
    return planRepository.findSummariesByUserId(userId).stream()
        .map(r -> new PlanSummaryResult(r.id(), r.name(), r.createdAt(), r.folderCount()))
        .toList();
  }
}
