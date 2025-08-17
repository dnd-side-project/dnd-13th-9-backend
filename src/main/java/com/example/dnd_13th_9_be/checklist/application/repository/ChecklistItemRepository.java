package com.example.dnd_13th_9_be.checklist.application.repository;

import com.example.dnd_13th_9_be.checklist.application.model.ChecklistItemModel;

public interface ChecklistItemRepository {
  boolean existsById(Long itemId);

  ChecklistItemModel findById(Long itemId);
}
