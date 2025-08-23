package com.example.dnd_13th_9_be.property.presentation.dto.response;

import com.example.dnd_13th_9_be.property.application.model.PropertyCategoryMemoModel;
import lombok.Builder;

@Builder
public record PropertyCategoryMemoResponse(Long categoryId, String categoryName, String memo) {
    public static PropertyCategoryMemoResponse of(PropertyCategoryMemoModel model) {
        return PropertyCategoryMemoResponse.builder()
            .categoryId(model.categoryId())
            .categoryName(model.categoryName())
            .memo(model.memo())
            .build();
    }
}
