package com.example.dnd_13th_9_be.property.application.model.converter;

import org.springframework.stereotype.Component;

import com.example.dnd_13th_9_be.common.support.converter.AbstractEntityConverter;
import com.example.dnd_13th_9_be.property.application.model.PropertyCategoryMemoModel;
import com.example.dnd_13th_9_be.property.persistence.entity.PropertyCategoryMemo;

@Component
public class PropertyCategoryMemoConverter
    implements AbstractEntityConverter<PropertyCategoryMemo, PropertyCategoryMemoModel> {

  @Override
  public PropertyCategoryMemoModel from(PropertyCategoryMemo propertyCategoryMemo) {
    return PropertyCategoryMemoModel.builder()
        .categoryId(propertyCategoryMemo.getCategory().getId())
        .categoryName(propertyCategoryMemo.getCategory().getName())
        .memo(propertyCategoryMemo.getMemo())
        .build();
  }

  @Override
  public PropertyCategoryMemo toEntity(PropertyCategoryMemoModel propertyCategoryMemoModel) {
    return null;
  }
}
