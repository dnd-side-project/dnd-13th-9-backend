package com.example.dnd_13th_9_be.checklist.persistence;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

import com.example.dnd_13th_9_be.checklist.application.model.ChecklistItemModel;
import com.example.dnd_13th_9_be.checklist.application.model.converter.ChecklistItemConverter;
import com.example.dnd_13th_9_be.checklist.application.repository.ChecklistItemRepository;
import com.example.dnd_13th_9_be.checklist.persistence.entity.ChecklistItem;
import com.example.dnd_13th_9_be.checklist.persistence.repository.JpaChecklistItemRepository;
import com.example.dnd_13th_9_be.global.error.BusinessException;

import static com.example.dnd_13th_9_be.global.error.ErrorCode.NOT_FOUND_CHECKLIST_ITEM;

@Repository
@RequiredArgsConstructor
public class ChecklistItemRepositoryImpl implements ChecklistItemRepository {
  private final JpaChecklistItemRepository jpaChecklistItemRepository;
  private final ChecklistItemConverter checklistItemConverter;

  @Override
  public boolean existsById(Long itemId) {
    return jpaChecklistItemRepository.existsById(itemId);
  }

  @Override
  public ChecklistItemModel findById(Long itemId) {
    ChecklistItem checklistItem =
        jpaChecklistItemRepository
            .findById(itemId)
            .orElseThrow(() -> new BusinessException(NOT_FOUND_CHECKLIST_ITEM));
    return checklistItemConverter.from(checklistItem);
  }
}
