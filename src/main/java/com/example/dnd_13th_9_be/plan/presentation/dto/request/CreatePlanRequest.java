package com.example.dnd_13th_9_be.plan.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "계획 생성 요청")
public record CreatePlanRequest(
    @Schema(description = "새로 생성할 계획 이름(최대 10자)", example = "새로 생성한 계획2", maxLength = 10)
        @NotBlank(message = "이름은 비어 있을 수 없습니다")
        @Size(min = 1, max = 10, message = "크기가 0에서 10 사이여야 합니다")
        String name) {}
