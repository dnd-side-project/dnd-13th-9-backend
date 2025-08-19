package com.example.dnd_13th_9_be.plan.presentation.dto.response;

import java.sql.Timestamp;

public record PlanSummaryResponse(
    Long planId, String name, Timestamp createdAt, long folderCount, boolean isDefaultPlan) {}
