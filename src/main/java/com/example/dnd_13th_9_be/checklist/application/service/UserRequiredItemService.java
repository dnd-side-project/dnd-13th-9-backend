package com.example.dnd_13th_9_be.checklist.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.example.dnd_13th_9_be.checklist.application.model.UserRequiredItemModel;
import com.example.dnd_13th_9_be.checklist.application.repository.ChecklistItemRepository;
import com.example.dnd_13th_9_be.checklist.application.repository.UserRequiredItemRepository;
import com.example.dnd_13th_9_be.global.error.BusinessException;
import com.example.dnd_13th_9_be.user.application.repository.UserRepository;

import static com.example.dnd_13th_9_be.global.error.ErrorCode.ALREADY_DELETED_USER_REQUIRED_ITEM;
import static com.example.dnd_13th_9_be.global.error.ErrorCode.ALREADY_EXISTS_USER_REQUIRED_ITEM;
import static com.example.dnd_13th_9_be.global.error.ErrorCode.NOT_FOUND_CHECKLIST_ITEM;
import static com.example.dnd_13th_9_be.global.error.ErrorCode.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserRequiredItemService {
  private final ChecklistItemRepository checklistItemRepository;
  private final UserRequiredItemRepository userRequiredItemRepository;
  private final UserRepository userRepository;

  @Transactional
  public void add(Long userId, Long itemId) {
    userRepository.findById(userId).orElseThrow(() -> new BusinessException(USER_NOT_FOUND));

    boolean isAlreadyExists = userRequiredItemRepository.existsById(userId, itemId);
    if (isAlreadyExists) {
      throw new BusinessException(ALREADY_EXISTS_USER_REQUIRED_ITEM);
    }

    boolean isNotExistsItem = !checklistItemRepository.existsById(itemId);
    if (isNotExistsItem) {
      throw new BusinessException(NOT_FOUND_CHECKLIST_ITEM);
    }

    UserRequiredItemModel userRequiredItemModel =
        UserRequiredItemModel.builder().userId(userId).itemId(itemId).build();
    userRequiredItemRepository.create(userRequiredItemModel);
  }

  @Transactional
  public void delete(Long userId, Long itemId) {
    boolean isNotExistsItem = !checklistItemRepository.existsById(itemId);
    if (isNotExistsItem) {
      throw new BusinessException(NOT_FOUND_CHECKLIST_ITEM);
    }

    long result = userRequiredItemRepository.delete(userId, itemId);
    if (result == 0) {
      throw new BusinessException(ALREADY_DELETED_USER_REQUIRED_ITEM);
    }
  }
}
