package com.example.dnd_13th_9_be.property.application.model.converter;

import com.example.dnd_13th_9_be.checklist.persistence.entity.ChecklistItem;
import com.example.dnd_13th_9_be.common.support.converter.AbstractEntityConverter;
import com.example.dnd_13th_9_be.folder.persistence.entity.Folder;
import com.example.dnd_13th_9_be.plan.persistence.entity.Plan;
import com.example.dnd_13th_9_be.property.application.model.PropertyRecordModel;
import com.example.dnd_13th_9_be.property.persistence.entity.Property;
import com.example.dnd_13th_9_be.property.persistence.entity.PropertyCategoryMemo;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PropertyRecordConverter implements AbstractEntityConverter<Property, PropertyRecordModel> {
    private final PropertyImageConverter imageConverter;
    private final PropertyImageConverter propertyImageConverter;
    private final PropertyRequiredCheckConverter requiredCheckConverter;
    private final ChecklistMemoConverter checklistMemoConverter;

    @Override
    public PropertyRecordModel from(Property property) {
        return PropertyRecordModel.builder()
            .propertyRecordId(property.getId())
            .images(property.getImages().stream().map(imageConverter::from).toList())
            .feeling(property.getFeeling())
            .propertyName(property.getTitle())
            .memo(property.getMemo())
            .referenceUrl(property.getReferenceUrl())
            .address(property.getAddress())
            .detailAddress(property.getDetailAddress())
            .longitude(property.getLongitude())
            .latitude(property.getLatitude())
            .contractType(property.getContractType())
            .houseType(property.getHouseType())
            .depositBig(property.getDepositBig())
            .depositSmall(property.getDepositSmall())
            .managementFee(property.getManagementFee())
            .moveInInfo(property.getMoveInInfo())
            .requiredChecklist(
                property.getRequiredChecklist().stream().map(requiredCheckConverter::from).toList())
            .requiredCheckMemo(property.getRequiredCheckMemo())
            .categoryMemo(property.getCategoryMemoList().stream().map(checklistMemoConverter::from).toList())
            .build();
    }

    @Override
    public Property toEntity(PropertyRecordModel propertyRecordModel) {
        return null;
    }

    public Property toEntity(Plan plan, Folder folder, PropertyRecordModel model, List<ChecklistItem> requiredCheckList, List<PropertyCategoryMemo> propertyCategoryMemoList) {
        return Property.builder()
            .folder(folder)
            .plan(plan)
            .title(model.propertyName())
            .feeling(model.feeling() == null ? null : model.feeling())
            .memo(model.memo())
            .referenceUrl(model.referenceUrl())
            .address(model.address())
            .detailAddress(model.detailAddress())
            .latitude(model.latitude())
            .longitude(model.longitude())
            .contractType(model.contractType())
            .houseType(model.houseType())
            .depositBig(model.depositBig())
            .depositSmall(model.depositSmall())
            .managementFee(model.managementFee())
            .moveInInfo(model.moveInInfo())
            .requiredCheckMemo(model.requiredCheckMemo())
            .images(model.images().stream().map(imageConverter::toEntity).toList())
            .requiredChecklist(requiredCheckList.stream().map(requiredCheckConverter::toEntity).toList())
            .categoryMemoList(propertyCategoryMemoList)
            .build();
    }
}
