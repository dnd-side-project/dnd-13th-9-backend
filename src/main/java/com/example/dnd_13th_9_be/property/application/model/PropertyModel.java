package com.example.dnd_13th_9_be.property.application.model;

import lombok.Builder;

import com.example.dnd_13th_9_be.property.persistence.entity.type.ContractType;
import com.example.dnd_13th_9_be.property.persistence.entity.type.FeelingType;
import com.example.dnd_13th_9_be.property.persistence.entity.type.HouseType;
import com.example.dnd_13th_9_be.property.presentation.dto.request.UpsertPropertyRequest;

@Builder
public record PropertyModel(
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
    Integer monthlyFee,
    String moveInInfo,
    String requiredCheckMemo) {
  public static PropertyModel from(UpsertPropertyRequest request) {
    return PropertyModel.builder()
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
        .monthlyFee(request.monthlyFee())
        .moveInInfo(request.moveInInfo())
        .requiredCheckMemo(request.getRequiredCheckMemo())
        .build();
  }
}
