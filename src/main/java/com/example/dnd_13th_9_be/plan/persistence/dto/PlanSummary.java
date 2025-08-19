package com.example.dnd_13th_9_be.plan.persistence.dto;

import java.sql.Timestamp;

public record PlanSummary(
    Long id, String name, Timestamp createdAt, long folderCount, boolean isDefault) {}
