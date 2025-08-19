package com.example.dnd_13th_9_be.plan.application.dto;

import java.sql.Timestamp;

public record PlanSummaryResult(
    Long planId, String name, Timestamp createdAt, long folderCount, boolean isDefault) {}
