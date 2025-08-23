package com.example.dnd_13th_9_be.checklist.persistence.repository;

import static com.example.dnd_13th_9_be.global.error.ErrorCode.NOT_FOUND_CATEGORY;
import static com.example.dnd_13th_9_be.global.error.ErrorCode.NOT_FOUND_FOLDER;

import com.example.dnd_13th_9_be.global.error.BusinessException;
import java.util.List;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

import com.example.dnd_13th_9_be.checklist.application.model.ChecklistCategoryModel;
import com.example.dnd_13th_9_be.checklist.application.model.converter.ChecklistCategoryConverter;
import com.example.dnd_13th_9_be.checklist.application.repository.ChecklistCategoryRepository;
import com.example.dnd_13th_9_be.checklist.persistence.entity.ChecklistCategory;

@Repository
@RequiredArgsConstructor
public class ChecklistCategoryRepositoryImpl implements ChecklistCategoryRepository {
  private final JpaChecklistCategoryRepository jpaChecklistCategoryRepository;
  private final ChecklistCategoryConverter checklistCategoryConverter;

  @Override
  public List<ChecklistCategoryModel> findAll() {
    List<ChecklistCategory> categories =
        jpaChecklistCategoryRepository.findAllByOrderBySortOrderAsc();
    return categories.stream().map(checklistCategoryConverter::from).toList();
  }

  @Override
  public void verifyById(Long categoryId) {
    var isNotExist = !jpaChecklistCategoryRepository.existsById(categoryId);
    if (isNotExist) {
      throw new BusinessException(NOT_FOUND_CATEGORY);
    }
  }
}
