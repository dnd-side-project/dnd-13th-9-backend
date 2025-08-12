package com.example.dnd_13th_9_be.plan.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.example.dnd_13th_9_be.plan.application.dto.PlanSummaryResult;

@Service
@RequiredArgsConstructor
public class PlanService {
  private final PlanQueryPort planQueryPort;

  @Transactional(readOnly = true)
  public List<PlanSummaryResult> getPlanList(Long userId) {
    return planQueryPort.findSummariesByUserId(userId);
  }
}
