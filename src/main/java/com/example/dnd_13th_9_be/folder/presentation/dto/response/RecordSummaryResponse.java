package com.example.dnd_13th_9_be.folder.presentation.dto.response;

import com.example.dnd_13th_9_be.folder.application.dto.RecordSummaryResult;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record RecordSummaryResponse(
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
    public static RecordSummaryResponse from(RecordSummaryResult result) {
        return RecordSummaryResponse.builder()
            .id(result.id())
            .imageUrl(result.imageUrl())
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
