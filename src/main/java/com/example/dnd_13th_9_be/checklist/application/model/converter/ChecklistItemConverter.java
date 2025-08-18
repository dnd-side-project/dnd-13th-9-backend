package com.example.dnd_13th_9_be.checklist.application.model.converter;

import org.springframework.stereotype.Component;

import com.example.dnd_13th_9_be.checklist.application.model.ChecklistItemModel;
import com.example.dnd_13th_9_be.checklist.persistence.entity.ChecklistItem;
import com.example.dnd_13th_9_be.common.support.converter.AbstractEntityConverter;

@Component
public class ChecklistItemConverter
    implements AbstractEntityConverter<ChecklistItem, ChecklistItemModel> {

  @Override
  public ChecklistItemModel from(ChecklistItem checklistItem) {
    return ChecklistItemModel.builder()
        .id(checklistItem.getId())
        .categoryId(checklistItem.getCategory().getId())
        .question(checklistItem.getQuestion())
        .description(checklistItem.getDescription())
        .sortOrder(checklistItem.getSortOrder())
        .build();
  }

  @Override
  public ChecklistItem toEntity(ChecklistItemModel checklistItemModel) {
    throw new UnsupportedOperationException("Use persistence layer repository with getReference()");
  }
}
