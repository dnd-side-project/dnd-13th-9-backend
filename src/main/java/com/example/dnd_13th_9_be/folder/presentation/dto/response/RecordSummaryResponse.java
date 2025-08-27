package com.example.dnd_13th_9_be.folder.presentation.dto.response;

import java.time.LocalDateTime;

import java.util.List;
import lombok.Builder;

import com.example.dnd_13th_9_be.folder.application.dto.RecordSummaryResult;

@Builder
public record RecordSummaryResponse(
    Long id,
    List<String> imageUrls,
    String recordType,
    String feeling,
    String title,
    String contractType,
    Integer depositBig,
    Integer depositSmall,
    Integer managementFee,
    String memo,
    String locationTag,
    Double latitude,
    Double longitude,
    LocalDateTime createdAt) {
  public static RecordSummaryResponse from(RecordSummaryResult result) {
    return RecordSummaryResponse.builder()
        .id(result.id())
        .imageUrls(result.imageUrl())
        .recordType(result.recordType())
        .feeling(result.feeling())
        .title(result.title())
        .contractType(result.contractType())
        .depositBig(result.depositBig())
        .depositSmall(result.depositSmall())
        .managementFee(result.managementFee())
        .memo(result.memo())
        .locationTag(result.locationTag())
        .latitude(result.latitude())
        .longitude(result.longitude())
        .createdAt(result.createdAt())
        .build();
  }
}
