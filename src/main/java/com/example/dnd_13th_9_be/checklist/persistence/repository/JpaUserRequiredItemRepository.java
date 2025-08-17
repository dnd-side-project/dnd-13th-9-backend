package com.example.dnd_13th_9_be.checklist.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.dnd_13th_9_be.checklist.persistence.entity.UserRequiredItem;

@Repository
public interface JpaUserRequiredItemRepository extends JpaRepository<UserRequiredItem, Long> {
  boolean existsByUserIdAndItemId(Long userId, Long itemId);

  long deleteByUserIdAndItemId(Long userId, Long itemId);

  List<UserRequiredItem> findAllByUserIdOrderByCreatedAtAsc(Long userId);
}
