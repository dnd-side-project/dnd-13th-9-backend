package com.example.dnd_13th_9_be.checklist.application.repository;

import java.util.List;

import com.example.dnd_13th_9_be.checklist.application.model.ChecklistCategoryModel;

public interface ChecklistCategoryRepository {
  List<ChecklistCategoryModel> findAll();
  void verifyById(Long categoryId);
}
