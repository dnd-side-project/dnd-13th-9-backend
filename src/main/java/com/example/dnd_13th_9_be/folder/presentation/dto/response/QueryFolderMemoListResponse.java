package com.example.dnd_13th_9_be.folder.presentation.dto.response;

import java.util.List;

import com.example.dnd_13th_9_be.placeMemo.application.dto.QueryPlaceMemoResponse;

public record QueryFolderMemoListResponse(
    List<RecordSummaryResponse> recordSummaryResponses,
    List<QueryPlaceMemoResponse> queryPlaceMemoResponses) {
  public static QueryFolderMemoListResponse of(
      List<RecordSummaryResponse> recordSummaryResponses,
      List<QueryPlaceMemoResponse> queryPlaceMemoResponses) {
    return new QueryFolderMemoListResponse(recordSummaryResponses, queryPlaceMemoResponses);
  }
}
