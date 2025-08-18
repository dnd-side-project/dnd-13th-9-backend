package com.example.dnd_13th_9_be.checklist.application.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.example.dnd_13th_9_be.checklist.application.model.ChecklistCategoryModel;
import com.example.dnd_13th_9_be.checklist.application.model.UserRequiredItemModel;
import com.example.dnd_13th_9_be.checklist.application.repository.ChecklistCategoryRepository;
import com.example.dnd_13th_9_be.checklist.application.repository.UserRequiredItemRepository;
import com.example.dnd_13th_9_be.checklist.presentation.dto.CategorySummary;
import com.example.dnd_13th_9_be.checklist.presentation.dto.ChecklistResponse;
import com.example.dnd_13th_9_be.checklist.presentation.dto.Section;
import com.example.dnd_13th_9_be.checklist.presentation.dto.SectionItem;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChecklistService {
  private final ChecklistCategoryRepository checklistCategoryRepository;
  private final UserRequiredItemRepository userRequiredItemRepository;

  public ChecklistResponse getChecklist(Long userId) {
    List<ChecklistCategoryModel> categories = checklistCategoryRepository.findAll();

    Set<Long> requiredIds =
        userRequiredItemRepository.findAllByUserId(userId).stream()
            .map(UserRequiredItemModel::getItemId)
            .collect(Collectors.toSet());

    List<CategorySummary> categorySummaries = new ArrayList<>();
    categorySummaries.add(new CategorySummary(0, "필수 확인"));

    List<SectionItem> requiredSectionItems = new ArrayList<>();
    List<Section> sections = new ArrayList<>();

    for (ChecklistCategoryModel category : categories) {
      categorySummaries.add(new CategorySummary(category.getSortOrder(), category.getName()));
      Map<Boolean, List<SectionItem>> partitionedItems =
          category.getItems().stream()
              .map(item -> new SectionItem(item.getId(), item.getQuestion(), item.getDescription()))
              .collect(Collectors.partitioningBy(item -> requiredIds.contains(item.id())));
      requiredSectionItems.addAll(partitionedItems.get(true));
      sections.add(new Section(category.getName(), partitionedItems.get(false)));
    }
    sections.addFirst(new Section("필수 확인", requiredSectionItems));
    return new ChecklistResponse(categorySummaries, sections);
  }
}
