package com.example.dnd_13th_9_be.placeMemo.application.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

public record QueryPlaceMemoListResponse(
    @Schema(description = "주변 메모 리스트") List<QueryPlaceMemoResponse> items) {
  public static QueryPlaceMemoListResponse of(List<QueryPlaceMemoResponse> items) {
    return new QueryPlaceMemoListResponse(items);
  }
}
