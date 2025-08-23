package com.example.dnd_13th_9_be.property.application.dto;

import lombok.Builder;

import com.example.dnd_13th_9_be.property.persistence.entity.type.ContractType;
import com.example.dnd_13th_9_be.property.persistence.entity.type.FeelingType;
import com.example.dnd_13th_9_be.property.persistence.entity.type.HouseType;
import com.example.dnd_13th_9_be.property.presentation.dto.request.UpsertPropertyRequest;

@Builder
public record PropertyDto(
    Long folderId,
    String title,
    FeelingType feeling,
    String memo,
    String referenceUrl,
    String address,
    String detailAddress,
    Double latitude,
    Double longitude,
    ContractType contractType,
    HouseType houseType,
    Integer depositBig,
    Integer depositSmall,
    Integer managementFee,
    String moveInInfo,
    String requiredCheckMemo) {
  public static PropertyDto from(UpsertPropertyRequest request) {
    return PropertyDto.builder()
        .folderId(request.folderId())
        .title(request.propertyName())
        .feeling(request.feeling())
        .memo(request.memo())
        .referenceUrl(request.referenceUrl())
        .address(request.address())
        .detailAddress(request.detailAddress())
        .latitude(request.latitude())
        .longitude(request.longitude())
        .contractType(request.contractType())
        .houseType(request.houseType())
        .depositBig(request.depositBig())
        .depositSmall(request.depositSmall())
        .managementFee(request.managementFee())
        .moveInInfo(request.moveInInfo())
        .requiredCheckMemo(request.getRequiredCheckMemo())
        .build();
  }
}
