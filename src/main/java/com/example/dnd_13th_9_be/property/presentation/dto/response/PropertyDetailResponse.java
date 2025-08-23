package com.example.dnd_13th_9_be.property.presentation.dto.response;

import com.example.dnd_13th_9_be.property.persistence.dto.PropertyCategoryMemoResult;
import com.example.dnd_13th_9_be.property.persistence.dto.PropertyImageResult;
import com.example.dnd_13th_9_be.property.persistence.dto.PropertyResult;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.Builder;

import com.example.dnd_13th_9_be.checklist.presentation.dto.ChecklistResponse;
import com.example.dnd_13th_9_be.checklist.presentation.dto.Section;
import com.example.dnd_13th_9_be.property.persistence.entity.type.ContractType;
import com.example.dnd_13th_9_be.property.persistence.entity.type.FeelingType;
import com.example.dnd_13th_9_be.property.persistence.entity.type.HouseType;

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

  public static PropertyDetailResponse of(
      PropertyResult property,
      List<PropertyCategoryMemoResult> categoryMemoList,
      List<PropertyImageResult> imageList,
      ChecklistResponse checklist
  ) {
        Map<Long, String> memoMap =
            categoryMemoList.stream()
            .collect(
                Collectors.toMap(
                    PropertyCategoryMemoResult::categoryId, PropertyCategoryMemoResult::memo));
    memoMap.put(0L, property.requiredCheckMemo());

    List<Section> updatedSections =
        checklist.sections().stream()
            .map(
                section ->
                    new Section(
                        section.categoryId(),
                        section.categoryName(),
                        memoMap.get(section.categoryId()),
                        section.items()))
            .toList();

    ChecklistResponse updatedChecklist =
        new ChecklistResponse(checklist.categories(), updatedSections);
    return PropertyDetailResponse.builder()
        .images(imageList.stream().map(PropertyImageResponse::from).toList())
        .propertyId(property.propertyId())
        .planId(property.planId())
        .planName(property.planName())
        .folderId(property.folderId())
        .folderName(property.folderName())
        .feeling(property.feeling())
        .propertyName(property.title())
        .memo(property.memo())
        .referenceUrl(property.referenceUrl())
        .address(property.address())
        .detailAddress(property.detailAddress())
        .longitude(property.longitude())
        .latitude(property.latitude())
        .contractType(property.contractType())
        .houseType(property.houseType())
        .depositBig(property.depositBig())
        .depositSmall(property.depositSmall())
        .managementFee(property.managementFee())
        .moveInInfo(property.moveInInfo())
        .checklist(updatedChecklist)
        .build();
  }
}
