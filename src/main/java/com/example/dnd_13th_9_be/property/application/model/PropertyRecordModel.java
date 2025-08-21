package com.example.dnd_13th_9_be.property.application.model;

import com.example.dnd_13th_9_be.common.support.AbstractModel;
import com.example.dnd_13th_9_be.property.persistence.entity.ContractType;
import com.example.dnd_13th_9_be.property.persistence.entity.FeelingType;
import com.example.dnd_13th_9_be.property.persistence.entity.HouseType;
import com.example.dnd_13th_9_be.property.presentation.dto.request.PropertyRecordRequest;
import java.util.List;
import lombok.Builder;

@Builder
public record PropertyRecordModel(
    Long propertyRecordId,
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
    List<ChecklistMemoModel> categoryMemo,
    Long planId,
    Long folderId
    ) implements AbstractModel {

    public static PropertyRecordModel from(List<PropertyImageModel> images, PropertyRecordRequest r, List<PropertyRequiredCheckModel> c) {
        return PropertyRecordModel.builder()
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
            .requiredCheckMemo(r.requiredCheckMemo())
            .requiredChecklist(c)
            .categoryMemo(r.categoriesMemo().stream().map(ChecklistMemoModel::from).toList())
            .planId(r.planId())
            .folderId(r.folderId())
            .build();
    }
}
