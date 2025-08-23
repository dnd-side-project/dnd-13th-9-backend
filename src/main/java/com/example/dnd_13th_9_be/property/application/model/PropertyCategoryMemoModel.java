package com.example.dnd_13th_9_be.property.application.model;

import lombok.Builder;

import com.example.dnd_13th_9_be.common.support.AbstractModel;
import com.example.dnd_13th_9_be.property.presentation.dto.request.PropertyCategoryMemoRequest;

@Builder
public record PropertyCategoryMemoModel(Long categoryId, String categoryName, String memo)
    implements AbstractModel {
  public static PropertyCategoryMemoModel from(PropertyCategoryMemoRequest r) {
    return PropertyCategoryMemoModel.builder().categoryId(r.categoryId()).memo(r.memo()).build();
  }
}
