package com.example.dnd_13th_9_be.plan.application.dto;

import java.time.LocalDateTime;

public record PlanDetailResult(
    Long planId, String name, LocalDateTime createdAt, boolean isDefault) {}
