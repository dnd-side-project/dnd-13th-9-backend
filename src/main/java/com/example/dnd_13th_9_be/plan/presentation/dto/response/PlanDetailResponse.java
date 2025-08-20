package com.example.dnd_13th_9_be.plan.presentation.dto.response;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "계획 상세 정보")
public record PlanDetailResponse(
    @Schema(description = "계획 고유 인덱스 값", example = "5") Long planId,
    @Schema(description = "계획 이름", example = "새로 생성한 계획2") String name,
    @Schema(description = "계획 생성일", example = "2025-08-12T21:47:01.903445") LocalDateTime createdAt,
    @Schema(description = "기본 계획 여부", example = "false") boolean isDefaultPlan) {}
