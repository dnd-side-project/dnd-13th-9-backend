package com.example.dnd_13th_9_be.checklist.presentation.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.example.dnd_13th_9_be.checklist.application.model.ChecklistCategoryModel;
import com.example.dnd_13th_9_be.checklist.application.model.ChecklistItemModel;

public record ChecklistResponse(List<CategorySummary> categories, List<Section> sections) {
  static final String REQUIRED_CHECK = "필수 확인";

  public static ChecklistResponse of(
      List<ChecklistCategoryModel> categories, Set<Long> requiredIds) {
    List<CategorySummary> categorySummaries = new ArrayList<>();
    categorySummaries.add(new CategorySummary(0, REQUIRED_CHECK));

    List<Section> sections = new ArrayList<>();

    for (ChecklistCategoryModel category : categories) {
      categorySummaries.add(new CategorySummary(category.getSortOrder(), category.getName()));
      List<SectionItem> items = new ArrayList<>();
      for (ChecklistItemModel item : category.getItems()) {
        SectionItem sectionItem =
            new SectionItem(
                item.getId(),
                item.getQuestion(),
                item.getDescription(),
                requiredIds.contains(item.getId()));
        items.add(sectionItem);
      }
      Section section = new Section(category.getId(), category.getName(), null, items);
      sections.add(section);
    }

    // TODO: 의미는 없지만 시간상 프론트에서 구조 변경이 어려워 구조 맞추기 위한 용도로 추가, 추후 구조 개선 필요
    sections.addFirst(new Section(0L, REQUIRED_CHECK, null, new ArrayList<>()));

    return new ChecklistResponse(categorySummaries, sections);
  }
}
