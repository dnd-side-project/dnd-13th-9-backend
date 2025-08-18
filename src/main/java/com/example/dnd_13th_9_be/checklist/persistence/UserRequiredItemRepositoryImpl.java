package com.example.dnd_13th_9_be.checklist.persistence;

import java.util.List;
import jakarta.persistence.EntityManager;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.example.dnd_13th_9_be.checklist.application.model.UserRequiredItemModel;
import com.example.dnd_13th_9_be.checklist.application.model.converter.UserRequiredItemConverter;
import com.example.dnd_13th_9_be.checklist.application.repository.UserRequiredItemRepository;
import com.example.dnd_13th_9_be.checklist.persistence.entity.ChecklistItem;
import com.example.dnd_13th_9_be.checklist.persistence.entity.UserRequiredItem;
import com.example.dnd_13th_9_be.checklist.persistence.repository.JpaUserRequiredItemRepository;
import com.example.dnd_13th_9_be.user.persistence.User;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserRequiredItemRepositoryImpl implements UserRequiredItemRepository {
  private final JpaUserRequiredItemRepository jpaUserRequiredItemRepository;
  private final UserRequiredItemConverter userRequiredItemConverter;
  private final EntityManager em;

  @Override
  public boolean existsById(Long userId, Long itemId) {
    return jpaUserRequiredItemRepository.existsByUserIdAndItemId(userId, itemId);
  }

  @Override
  @Transactional
  public UserRequiredItemModel create(UserRequiredItemModel userRequiredItemModel) {
    User userRef = em.getReference(User.class, userRequiredItemModel.getUserId());
    ChecklistItem checklistItemRef =
        em.getReference(ChecklistItem.class, userRequiredItemModel.getItemId());
    UserRequiredItem entity =
        UserRequiredItem.builder().user(userRef).item(checklistItemRef).build();

    UserRequiredItem savedUserRequiredItem = jpaUserRequiredItemRepository.save(entity);
    return userRequiredItemConverter.from(savedUserRequiredItem);
  }

  @Override
  @Transactional
  public long delete(Long userId, Long itemId) {
    return jpaUserRequiredItemRepository.deleteByUserIdAndItemId(userId, itemId);
  }

  @Override
  public List<UserRequiredItemModel> findAllByUserId(Long userId) {
    List<UserRequiredItem> requiredItems =
        jpaUserRequiredItemRepository.findAllByUserIdOrderByCreatedAtAsc(userId);
    return requiredItems.stream().map(userRequiredItemConverter::from).toList();
  }
}
