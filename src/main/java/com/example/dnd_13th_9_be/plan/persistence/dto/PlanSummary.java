package com.example.dnd_13th_9_be.plan.persistence.dto;

import java.time.LocalDateTime;

public record PlanSummary(Long id, String name, LocalDateTime createdAt, long folderCount) {}
