package com.example.dnd_13th_9_be.property.presentation.dto.response;

import lombok.Builder;

@Builder
public record PropertyCategoryMemoResponse(Long categoryId, String categoryName, String memo) {}
