package com.example.dnd_13th_9_be.checklist.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.dnd_13th_9_be.checklist.persistence.entity.ChecklistCategory;

public interface JpaChecklistCategoryRepository extends JpaRepository<ChecklistCategory, Long> {
  @EntityGraph(attributePaths = "items")
  List<ChecklistCategory> findAllByOrderBySortOrderAsc();
}
