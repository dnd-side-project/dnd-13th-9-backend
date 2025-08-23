package com.example.dnd_13th_9_be.property.presentation.dto.response;

import com.example.dnd_13th_9_be.checklist.presentation.dto.Section;
import com.example.dnd_13th_9_be.property.application.model.PropertyCategoryMemoModel;
import com.example.dnd_13th_9_be.property.application.model.PropertyModel;
import java.util.List;

import com.example.dnd_13th_9_be.checklist.presentation.dto.ChecklistResponse;
import com.example.dnd_13th_9_be.property.persistence.entity.type.ContractType;
import com.example.dnd_13th_9_be.property.persistence.entity.type.FeelingType;
import com.example.dnd_13th_9_be.property.persistence.entity.type.HouseType;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Builder;

@Builder
public record PropertyDetailResponse(
    List<PropertyImageResponse> images,
    Long propertyId,
    Long planId,
    String planName,
    Long folderId,
    String folderName,
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
    ChecklistResponse checklist) {
    public static PropertyDetailResponse of(PropertyModel m, ChecklistResponse checklist) {
        Map<Long, String> memoMap = m.categoryMemo().stream()
            .collect(Collectors.toMap(
                PropertyCategoryMemoModel::categoryId,
                PropertyCategoryMemoModel::memo
            ));
        memoMap.put(0L, m.requiredCheckMemo());

        List<Section> updatedSections = checklist.sections().stream()
            .map(section -> new Section(
                section.categoryId(),
                section.categoryName(),
                memoMap.get(section.categoryId()),
                section.items()
            ))
            .toList();

        ChecklistResponse updatedChecklist = new ChecklistResponse(
            checklist.categories(),
            updatedSections
        );

        return PropertyDetailResponse.builder()
            .images(m.images().stream().map(PropertyImageResponse::of).toList())
            .propertyId(m.propertyId())
            .planId(m.planId())
            .planName(m.planName())
            .folderId(m.folderId())
            .folderName(m.folderName())
            .feeling(m.feeling())
            .propertyName(m.propertyName())
            .memo(m.memo())
            .referenceUrl(m.referenceUrl())
            .address(m.address())
            .detailAddress(m.detailAddress())
            .longitude(m.longitude())
            .latitude(m.latitude())
            .contractType(m.contractType())
            .houseType(m.houseType())
            .depositBig(m.depositBig())
            .depositSmall(m.depositSmall())
            .managementFee(m.managementFee())
            .moveInInfo(m.moveInInfo())
            .checklist(updatedChecklist)
            .build();
    }
}
