package com.example.dnd_13th_9_be.property.application.model;

import java.util.List;

import lombok.Builder;

import com.example.dnd_13th_9_be.common.support.AbstractModel;
import com.example.dnd_13th_9_be.property.persistence.entity.type.ContractType;
import com.example.dnd_13th_9_be.property.persistence.entity.type.FeelingType;
import com.example.dnd_13th_9_be.property.persistence.entity.type.HouseType;
import com.example.dnd_13th_9_be.property.presentation.dto.request.PropertyCategoryMemoRequest;
import com.example.dnd_13th_9_be.property.presentation.dto.request.UpsertPropertyRequest;

@Builder
public record PropertyModel(
    Long propertyId,
    List<PropertyImageModel> images,
    FeelingType feeling,
    String propertyName,
    String memo,
    String referenceUrl,
    String address,
    String detailAddress,
    Double longitude,
    Double latitude,
    ContractType contractType,
    HouseType houseType,
    Integer depositBig,
    Integer depositSmall,
    Integer managementFee,
    String moveInInfo,
    List<PropertyRequiredCheckModel> requiredChecklist,
    String requiredCheckMemo,
    List<PropertyCategoryMemoModel> categoryMemo,
    Long folderId,
    String folderName,
    Long planId,
    String planName)
    implements AbstractModel {

  public static PropertyModel from(
      List<PropertyImageModel> images,
      UpsertPropertyRequest r,
      List<PropertyRequiredCheckModel> c) {

    String requiredCheckMemo =
        r.categoryMemoList().stream()
            .filter(m -> m.categoryId() == 0L)
            .findFirst()
            .map(PropertyCategoryMemoRequest::memo)
            .orElse(null);
    r.categoryMemoList().removeIf(m -> m.categoryId() == 0L);

    return PropertyModel.builder()
        .images(images)
        .feeling(r.feeling())
        .propertyName(r.propertyName())
        .memo(r.memo())
        .referenceUrl(r.referenceUrl())
        .address(r.address())
        .detailAddress(r.detailAddress())
        .longitude(r.longitude())
        .latitude(r.latitude())
        .contractType(r.contractType())
        .houseType(r.houseType())
        .depositBig(r.depositBig())
        .depositSmall(r.depositSmall())
        .managementFee(r.managementFee())
        .moveInInfo(r.moveInInfo())
        .requiredCheckMemo(requiredCheckMemo)
        .requiredChecklist(c)
        .categoryMemo(r.categoryMemoList().stream().map(PropertyCategoryMemoModel::from).toList())
        .folderId(r.folderId())
        .build();
  }
}
