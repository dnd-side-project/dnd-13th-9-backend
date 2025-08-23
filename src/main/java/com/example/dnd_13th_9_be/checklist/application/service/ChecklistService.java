package com.example.dnd_13th_9_be.checklist.application.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.example.dnd_13th_9_be.checklist.application.model.ChecklistCategoryModel;
import com.example.dnd_13th_9_be.checklist.application.model.UserRequiredItemModel;
import com.example.dnd_13th_9_be.checklist.application.repository.ChecklistCategoryRepository;
import com.example.dnd_13th_9_be.checklist.application.repository.UserRequiredItemRepository;
import com.example.dnd_13th_9_be.checklist.presentation.dto.ChecklistResponse;

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

    return ChecklistResponse.of(categories, requiredIds);
  }
}
