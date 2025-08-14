package com.example.dnd_13th_9_be.plan.application.port;

import com.example.dnd_13th_9_be.plan.application.dto.PlanDetailResult;

public interface PlanCommandPort {
  PlanDetailResult create(Long userId, String name, boolean isDefault);

  boolean rename(Long planId, String newName);

  boolean delete(Long planId);
}
