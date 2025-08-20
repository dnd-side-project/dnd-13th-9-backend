package com.example.dnd_13th_9_be.folder.presentation.dto.response;

import java.time.LocalDateTime;

import com.example.dnd_13th_9_be.folder.application.dto.FolderDetailResult;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "폴더 상세 정보")
public record FolderDetailResponse(
    @Schema(description = "폴더 고유 인덱스 값", example = "2") Long folderId,
    @Schema(description = "폴더 이름", example = "새로운폴더1234") String name,
    @Schema(description = "폴더 생성일", example = "2025-08-12T16:25:14.955725") LocalDateTime createdAt,
    @Schema(description = "기본 폴더 여부", example = "false") boolean isDefaultFolder) {
  public static FolderDetailResponse from(FolderDetailResult result) {
    return new FolderDetailResponse(
        result.folderId(), result.name(), result.createdAt(), result.isDefault());
  }
}
