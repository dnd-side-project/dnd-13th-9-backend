package com.example.dnd_13th_9_be.plan.presentation.dto.response;

import java.sql.Timestamp;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "계획 요약 정보")
public record PlanSummaryResponse(
    @Schema(description = "계획 고유 인덱스 값", example = "4") Long planId,
    @Schema(description = "계획 이름", example = "새로 생성한 계획") String name,
    @Schema(description = "계획 생성일", example = "2025-08-12T21:43:18.429603") Timestamp createdAt,
    @Schema(description = "계획에 저장된 폴더 갯수", example = "0") long folderCount,
    @Schema(description = "기본 계획 여부", example = "false") boolean isDefaultPlan) {}
