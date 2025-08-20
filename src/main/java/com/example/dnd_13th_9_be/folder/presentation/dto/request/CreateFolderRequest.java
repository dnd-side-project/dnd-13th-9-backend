package com.example.dnd_13th_9_be.folder.presentation.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "폴더 생성 요청")
public record CreateFolderRequest(
    @Schema(description = "폴더를 포함시킬 계획 고유 인덱스 값", example = "1") @NotNull @Min(1) Long planId,
    @Schema(description = "새로 생성할 폴더 이름(최대 10자)", example = "테스트폴더", maxLength = 10)
        @NotBlank(message = "이름은 비어 있을 수 없습니다")
        @Size(min = 1, max = 10, message = "크기가 1에서 10 사이여야 합니다")
        String name) {}
