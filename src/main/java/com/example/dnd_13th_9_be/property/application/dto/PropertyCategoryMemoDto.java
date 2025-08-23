package com.example.dnd_13th_9_be.property.application.dto;

import com.example.dnd_13th_9_be.property.presentation.dto.request.PropertyCategoryMemoRequest;
import com.example.dnd_13th_9_be.property.presentation.dto.request.UpsertPropertyRequest;
import lombok.Builder;

@Builder
public record PropertyCategoryMemoDto(
    Long propertyId,
    Long categoryId,
    String memo
) {
    public static PropertyCategoryMemoDto from(Long propertyId, PropertyCategoryMemoRequest request) {
        return PropertyCategoryMemoDto.builder()
            .propertyId(propertyId)
            .categoryId(request.categoryId())
            .memo(request.memo())
            .build();
    }
}
