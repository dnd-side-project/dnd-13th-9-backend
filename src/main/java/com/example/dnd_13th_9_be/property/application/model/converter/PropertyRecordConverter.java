package com.example.dnd_13th_9_be.property.application.model.converter;

import com.example.dnd_13th_9_be.checklist.persistence.entity.ChecklistCategory;
import com.example.dnd_13th_9_be.checklist.persistence.entity.ChecklistItem;
import com.example.dnd_13th_9_be.common.support.converter.AbstractEntityConverter;
import com.example.dnd_13th_9_be.folder.persistence.entity.Folder;
import com.example.dnd_13th_9_be.plan.persistence.entity.Plan;
import com.example.dnd_13th_9_be.property.application.model.PropertyRecordModel;
import com.example.dnd_13th_9_be.property.persistence.entity.ContractType;
import com.example.dnd_13th_9_be.property.persistence.entity.FeelingType;
import com.example.dnd_13th_9_be.property.persistence.entity.PropertyCategoryMemo;
import com.example.dnd_13th_9_be.property.persistence.entity.PropertyRecord;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PropertyRecordConverter implements AbstractEntityConverter<PropertyRecord, PropertyRecordModel> {
    private final PropertyImageConverter imageConverter;
    private final PropertyImageConverter propertyImageConverter;
    private final PropertyRequiredCheckConverter requiredCheckConverter;
    private final ChecklistMemoConverter checklistMemoConverter;

    @Override
    public PropertyRecordModel from(PropertyRecord propertyRecord) {
        return PropertyRecordModel.builder()
            .propertyRecordId(propertyRecord.getId())
            .images(propertyRecord.getImages().stream().map(imageConverter::from).toList())
            .feeling(propertyRecord.getFeeling())
            .propertyName(propertyRecord.getTitle())
            .memo(propertyRecord.getMemo())
            .referenceUrl(propertyRecord.getReferenceUrl())
            .address(propertyRecord.getAddress())
            .detailAddress(propertyRecord.getDetailAddress())
            .longitude(propertyRecord.getLongitude())
            .latitude(propertyRecord.getLatitude())
            .contractType(propertyRecord.getContractType())
            .houseType(propertyRecord.getHouseType())
            .depositBig(propertyRecord.getDepositBig())
            .depositSmall(propertyRecord.getDepositSmall())
            .managementFee(propertyRecord.getManagementFee())
            .moveInInfo(propertyRecord.getMoveInInfo())
            .requiredChecklist(propertyRecord.getRequiredChecklist().stream().map(requiredCheckConverter::from).toList())
            .requiredCheckMemo(propertyRecord.getRequiredCheckMemo())
            .categoryMemo(propertyRecord.getCategoryMemoList().stream().map(checklistMemoConverter::from).toList())
            .build();
    }

    @Override
    public PropertyRecord toEntity(PropertyRecordModel propertyRecordModel) {
        return null;
    }

    public PropertyRecord toEntity(Plan plan, Folder folder, PropertyRecordModel model, List<ChecklistItem> requiredCheckList, List<PropertyCategoryMemo> propertyCategoryMemoList) {
        return PropertyRecord.builder()
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
