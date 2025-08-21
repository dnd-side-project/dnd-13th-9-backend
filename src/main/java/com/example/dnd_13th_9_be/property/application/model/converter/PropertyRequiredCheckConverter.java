package com.example.dnd_13th_9_be.property.application.model.converter;

import com.example.dnd_13th_9_be.checklist.persistence.entity.ChecklistItem;
import com.example.dnd_13th_9_be.common.support.converter.AbstractEntityConverter;
import com.example.dnd_13th_9_be.property.application.model.PropertyRequiredCheckModel;
import com.example.dnd_13th_9_be.property.persistence.entity.PropertyRequiredCheck;
import org.springframework.stereotype.Component;

@Component
public class PropertyRequiredCheckConverter implements AbstractEntityConverter<PropertyRequiredCheck, PropertyRequiredCheckModel> {

    @Override
    public PropertyRequiredCheckModel from(PropertyRequiredCheck propertyRequiredCheck) {
        return PropertyRequiredCheckModel.builder()
            .propertyRequiredCheckId(propertyRequiredCheck.getId())
            .checklistId(propertyRequiredCheck.getChecklistItem().getId())
            .build();
    }

    @Override
    public PropertyRequiredCheck toEntity(PropertyRequiredCheckModel propertyRequiredCheckModel) {
        return null;
    }

    public PropertyRequiredCheck toEntity(ChecklistItem checklistItem) {
        return PropertyRequiredCheck.builder()
            .checklistItem(checklistItem)
            .build();
    }
}
