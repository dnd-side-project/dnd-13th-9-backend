package com.example.dnd_13th_9_be.plan.presentation.dto.response;

import java.time.LocalDateTime;

public record PlanDetailResponse(Long planId, String name, LocalDateTime createdA, boolean isDefaultPlan) {}
