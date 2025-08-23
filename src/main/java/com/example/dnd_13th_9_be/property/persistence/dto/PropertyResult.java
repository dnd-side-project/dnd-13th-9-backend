package com.example.dnd_13th_9_be.property.persistence.dto;

import com.example.dnd_13th_9_be.property.persistence.entity.Property;
import com.example.dnd_13th_9_be.property.persistence.entity.type.ContractType;
import com.example.dnd_13th_9_be.property.persistence.entity.type.FeelingType;
import com.example.dnd_13th_9_be.property.persistence.entity.type.HouseType;
import lombok.Builder;

@Builder
public record PropertyResult(
    Long propertyId,
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
    String requiredCheckMemo
) {
    public static PropertyResult from(Property property) {
        return PropertyResult.builder()
            .propertyId(property.getId())
            .folderId(property.getFolder().getId())
            .title(property.getTitle())
            .feeling(property.getFeeling())
            .memo(property.getMemo())
            .referenceUrl(property.getReferenceUrl())
            .address(property.getAddress())
            .detailAddress(property.getDetailAddress())
            .latitude(property.getLatitude())
            .longitude(property.getLongitude())
            .contractType(property.getContractType())
            .houseType(property.getHouseType())
            .depositBig(property.getDepositBig())
            .depositSmall(property.getDepositSmall())
            .managementFee(property.getManagementFee())
            .moveInInfo(property.getMoveInInfo())
            .requiredCheckMemo(property.getRequiredCheckMemo())
            .build();
    }
}
