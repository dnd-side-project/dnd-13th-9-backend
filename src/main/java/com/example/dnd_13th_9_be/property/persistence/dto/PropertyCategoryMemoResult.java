package com.example.dnd_13th_9_be.property.persistence.dto;

import com.example.dnd_13th_9_be.property.persistence.entity.PropertyCategoryMemo;
import lombok.Builder;

@Builder
public record PropertyCategoryMemoResult(
    Long id,
    Long categoryId,
    String memo
) {
    public static PropertyCategoryMemoResult from(PropertyCategoryMemo entity) {
        return PropertyCategoryMemoResult.builder()
            .id(entity.getId())
            .categoryId(entity.getCategory().getId())
            .memo(entity.getMemo())
            .build();
    }
}
