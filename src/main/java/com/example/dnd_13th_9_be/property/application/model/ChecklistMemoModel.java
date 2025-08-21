package com.example.dnd_13th_9_be.property.application.model;

import com.example.dnd_13th_9_be.common.support.AbstractModel;
import com.example.dnd_13th_9_be.property.presentation.dto.request.ChecklistMemoRequest;
import lombok.Builder;

@Builder
public record ChecklistMemoModel(
    Long categoryId,
    String categoryName,
    String memo
) implements AbstractModel {
    public static ChecklistMemoModel from(ChecklistMemoRequest r) {
        return ChecklistMemoModel.builder()
            .categoryId(r.categoryId())
            .memo(r.memo())
            .build();
    }
}
