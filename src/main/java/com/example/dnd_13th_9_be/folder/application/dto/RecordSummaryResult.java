package com.example.dnd_13th_9_be.folder.application.dto;

import com.example.dnd_13th_9_be.folder.persistence.dto.RecordSummary.RecordImageSummary;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;

import com.example.dnd_13th_9_be.folder.persistence.dto.RecordSummary;

@Builder
public record RecordSummaryResult(
    Long id,
    List<String> imageUrl,
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
  public static RecordSummaryResult from(RecordSummary recordSummary) {

    return RecordSummaryResult.builder()
        .id(recordSummary.id())
        .imageUrl(recordSummary.images().stream().map(RecordImageSummary::url).toList())
        .recordType(recordSummary.recordType() != null ? recordSummary.recordType().name() : null)
        .feeling(recordSummary.feeling() != null ? recordSummary.feeling().name() : null)
        .title(recordSummary.title())
        .contractType(
            recordSummary.contractType() != null ? recordSummary.contractType().name() : null)
        .depositBig(recordSummary.depositBig())
        .depositSmall(recordSummary.depositSmall())
        .managementFee(recordSummary.managementFee())
        .memo(recordSummary.memo())
        .locationTag(recordSummary.locationTag())
        .latitude(recordSummary.latitude())
        .longitude(recordSummary.longitude())
        .createdAt(recordSummary.createdAt().toLocalDateTime())
        .build();
  }
}
