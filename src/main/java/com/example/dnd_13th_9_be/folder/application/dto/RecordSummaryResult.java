package com.example.dnd_13th_9_be.folder.application.dto;

import com.example.dnd_13th_9_be.folder.persistence.dto.RecordSummary;
import com.example.dnd_13th_9_be.folder.persistence.dto.RecordSummary.RecordImageSummary;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import lombok.Builder;

@Builder
public record RecordSummaryResult(
    Long id,
    String imageUrl,
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
    LocalDateTime createdAt
) {
    public static RecordSummaryResult from(RecordSummary recordSummary) {
        RecordImageSummary image =
            Optional.ofNullable(recordSummary.images())
                .orElseGet(Collections::emptyList)
                .stream()
                .filter(i -> Objects.equals(i.order(), 1))
                .findFirst()
                .orElse(null);
        return RecordSummaryResult.builder()
            .id(recordSummary.id())
            .imageUrl(image == null ? null : image.url())
            .recordType(recordSummary.recordType().name())
            .feeling(recordSummary.feeling().name())
            .title(recordSummary.title())
            .contractType(recordSummary.contractType().name())
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
