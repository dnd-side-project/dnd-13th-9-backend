package com.example.dnd_13th_9_be.property.application.model;

import lombok.Builder;

import com.example.dnd_13th_9_be.property.persistence.dto.RecentPropertyResult;

@Builder
public record RecentPropertyModel(
    Long propertyId,
    String imageUrl,
    String feeling,
    String title,
    Integer depositBig,
    Integer depositSmall,
    Integer managementFee,
    String contractType,
    String planName,
    String folderName) {
  public static RecentPropertyModel from(RecentPropertyResult result) {
    return RecentPropertyModel.builder()
        .propertyId(result.propertyId())
        .imageUrl(result.imageUrl())
        .feeling(result.feeling() == null ? null : result.feeling().name())
        .title(result.title())
        .depositBig(result.depositBig())
        .depositSmall(result.depositSmall())
        .managementFee(result.managementFee())
        .contractType(result.contractType() == null ? null : result.contractType().name())
        .planName(result.planName())
        .folderName(result.folderName())
        .build();
  }
}
