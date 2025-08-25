package com.example.dnd_13th_9_be.checklist.presentation.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.example.dnd_13th_9_be.checklist.application.model.ChecklistCategoryModel;

public record ChecklistResponse(List<CategorySummary> categories, List<Section> sections) {
  static final String REQUIRED_CHECK = "필수 확인";

  public static ChecklistResponse of(
      List<ChecklistCategoryModel> categories, Set<Long> requiredIds) {
    List<CategorySummary> categorySummaries = new ArrayList<>();
    categorySummaries.add(new CategorySummary(0, REQUIRED_CHECK));

    List<SectionItem> requiredSectionItems = new ArrayList<>();
    List<Section> sections = new ArrayList<>();

    for (ChecklistCategoryModel category : categories) {
      categorySummaries.add(new CategorySummary(category.getSortOrder(), category.getName()));
      Map<Boolean, List<SectionItem>> partitionedItems =
          category.getItems().stream()
              .map(item -> new SectionItem(item.getId(), item.getQuestion(), item.getDescription()))
              .collect(Collectors.partitioningBy(item -> requiredIds.contains(item.itemId())));
      requiredSectionItems.addAll(partitionedItems.get(true));
      sections.add(
          new Section(category.getId(), category.getName(), null, partitionedItems.get(false)));
    }
    sections.addFirst(new Section(0L, REQUIRED_CHECK, null, requiredSectionItems));

    return new ChecklistResponse(categorySummaries, sections);
  }
}
