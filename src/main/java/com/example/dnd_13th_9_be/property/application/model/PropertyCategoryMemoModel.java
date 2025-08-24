package com.example.dnd_13th_9_be.property.application.model;

import lombok.Builder;

import com.example.dnd_13th_9_be.property.presentation.dto.request.PropertyCategoryMemoRequest;

@Builder
public record PropertyCategoryMemoModel(Long propertyId, Long categoryId, String memo) {
  public static PropertyCategoryMemoModel from(
      Long propertyId, PropertyCategoryMemoRequest request) {
    return PropertyCategoryMemoModel.builder()
        .propertyId(propertyId)
        .categoryId(request.categoryId())
        .memo(request.memo())
        .build();
  }
}
