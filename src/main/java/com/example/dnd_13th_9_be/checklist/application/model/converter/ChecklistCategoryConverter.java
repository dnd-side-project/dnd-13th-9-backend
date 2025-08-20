package com.example.dnd_13th_9_be.checklist.application.model.converter;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.dnd_13th_9_be.checklist.application.model.ChecklistCategoryModel;
import com.example.dnd_13th_9_be.checklist.application.model.ChecklistItemModel;
import com.example.dnd_13th_9_be.checklist.persistence.entity.ChecklistCategory;
import com.example.dnd_13th_9_be.common.support.converter.AbstractEntityConverter;

@Component
public class ChecklistCategoryConverter
    implements AbstractEntityConverter<ChecklistCategory, ChecklistCategoryModel> {

  @Override
  public ChecklistCategoryModel from(ChecklistCategory c) {
    return ChecklistCategoryModel.builder()
        .id(c.getId())
        .name(c.getName())
        .sortOrder(c.getSortOrder())
        .items(
            c.getItems() == null
                ? List.of()
                : c.getItems().stream()
                    .map(
                        i ->
                            ChecklistItemModel.builder()
                                .id(i.getId())
                                .categoryId(c.getId())
                                .question(i.getQuestion())
                                .description(i.getDescription())
                                .sortOrder(i.getSortOrder())
                                .build())
                    .toList())
        .build();
  }

  @Override
  public ChecklistCategory toEntity(ChecklistCategoryModel m) {
    return ChecklistCategory.builder().name(m.getName()).sortOrder(m.getSortOrder()).build();
  }
}
