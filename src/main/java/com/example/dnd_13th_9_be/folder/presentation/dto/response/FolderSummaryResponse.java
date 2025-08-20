package com.example.dnd_13th_9_be.folder.presentation.dto.response;

import java.time.LocalDateTime;

import com.example.dnd_13th_9_be.folder.application.dto.FolderSummaryResult;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "폴더 요약 정보")
public record FolderSummaryResponse(
    @Schema(description = "폴더 고유 인덱스 값", example = "13") Long folderId,
    @Schema(description = "폴더 이름", example = "테스트 폴더 1") String name,
    @Schema(description = "폴더 생성일", example = "2025-08-12T21:50:44.801487") LocalDateTime createdAt,
    @Schema(description = "폴더에 저장된 기록 수(매물 기록 + 주변 장소 메모)", example = "0") long recordCount,
    @Schema(description = "기본 폴더 여부", example = "false") boolean isDefaultFolder) {
  public static FolderSummaryResponse from(FolderSummaryResult result) {
    return new FolderSummaryResponse(
        result.folderId(),
        result.name(),
        result.createdAt(),
        result.recordCount(),
        result.isDefaultFolder());
  }
}
