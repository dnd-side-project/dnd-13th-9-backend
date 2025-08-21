package com.example.dnd_13th_9_be.property.application.model.converter;

import com.example.dnd_13th_9_be.common.support.converter.AbstractEntityConverter;
import com.example.dnd_13th_9_be.property.application.model.ChecklistMemoModel;
import com.example.dnd_13th_9_be.property.persistence.entity.PropertyCategoryMemo;
import org.springframework.stereotype.Component;

@Component
public class ChecklistMemoConverter implements AbstractEntityConverter<PropertyCategoryMemo, ChecklistMemoModel> {

    @Override
    public ChecklistMemoModel from(PropertyCategoryMemo propertyCategoryMemo) {
        return ChecklistMemoModel.builder()
            .categoryId(propertyCategoryMemo.getId())
            .categoryName(propertyCategoryMemo.getCategory().getName())
            .memo(propertyCategoryMemo.getMemo())
            .build();
    }

    @Override
    public PropertyCategoryMemo toEntity(ChecklistMemoModel checklistMemoModel) {
        return null;
    }
}
