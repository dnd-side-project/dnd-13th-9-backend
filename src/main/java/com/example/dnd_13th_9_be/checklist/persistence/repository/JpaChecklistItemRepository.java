package com.example.dnd_13th_9_be.checklist.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.dnd_13th_9_be.checklist.persistence.entity.ChecklistItem;

public interface JpaChecklistItemRepository extends JpaRepository<ChecklistItem, Long> {}
