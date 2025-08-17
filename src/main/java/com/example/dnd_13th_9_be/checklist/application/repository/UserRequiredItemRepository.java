package com.example.dnd_13th_9_be.checklist.application.repository;

import java.util.List;

import com.example.dnd_13th_9_be.checklist.application.model.UserRequiredItemModel;

public interface UserRequiredItemRepository {
  boolean existsById(Long userId, Long itemId);

  UserRequiredItemModel create(UserRequiredItemModel userRequiredItemModel);

  long delete(Long userId, Long itemId);

  List<UserRequiredItemModel> findAllByUserId(Long userId);
}
