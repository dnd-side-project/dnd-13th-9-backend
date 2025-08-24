package com.example.dnd_13th_9_be.property.application.repository;

import java.util.List;

import com.example.dnd_13th_9_be.property.application.model.PropertyCategoryMemoModel;
import com.example.dnd_13th_9_be.property.persistence.dto.PropertyCategoryMemoResult;

public interface PropertyCategoryMemoRepository {
  void save(PropertyCategoryMemoModel dto);

  List<PropertyCategoryMemoResult> findAllByPropertyId(Long propertyId);

  void update(PropertyCategoryMemoModel dto);

  void deleteAllByPropertyId(Long propertyId);

  void deleteByCategoryIdAndPropertyId(Long categoryId, Long propertyId);
}
