package com.example.dnd_13th_9_be.property.application.repository;

import com.example.dnd_13th_9_be.property.application.dto.PropertyCategoryMemoDto;
import com.example.dnd_13th_9_be.property.persistence.dto.PropertyCategoryMemoResult;
import java.util.List;

public interface PropertyCategoryMemoRepository {
  void save(PropertyCategoryMemoDto dto);
  List<PropertyCategoryMemoResult> findAllById(Long propertyId);
}
