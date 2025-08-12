package com.example.dnd_13th_9_be.plan.application.dto;

import java.time.LocalDateTime;

public record PlanSummaryResult(
    Long planId, String name, LocalDateTime createdAt, long folderCount) {}
