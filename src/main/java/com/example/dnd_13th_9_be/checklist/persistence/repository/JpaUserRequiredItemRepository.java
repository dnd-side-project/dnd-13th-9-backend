package com.example.dnd_13th_9_be.checklist.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.dnd_13th_9_be.checklist.persistence.entity.UserRequiredItem;

@Repository
public interface JpaUserRequiredItemRepository extends JpaRepository<UserRequiredItem, Long> {
  boolean existsByUserIdAndItemId(Long userId, Long itemId);

  @Modifying(clearAutomatically = true, flushAutomatically = true)
  @Transactional
  long deleteByUserIdAndItemId(Long userId, Long itemId);

  @EntityGraph(attributePaths = {"user", "item"})
  List<UserRequiredItem> findAllByUserIdOrderByCreatedAtAsc(Long userId);
}
