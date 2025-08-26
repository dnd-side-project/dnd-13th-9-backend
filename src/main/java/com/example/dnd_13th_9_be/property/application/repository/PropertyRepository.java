package com.example.dnd_13th_9_be.property.application.repository;

import java.util.List;

import com.example.dnd_13th_9_be.property.application.model.PropertyModel;
import com.example.dnd_13th_9_be.property.persistence.dto.PropertyResult;
import com.example.dnd_13th_9_be.property.persistence.dto.RecentPropertyResult;

public interface PropertyRepository {
  void verifyExistsById(Long userId, Long propertyId);

  void delete(Long userId, Long propertyId);

  PropertyResult findById(Long propertyId);

  PropertyResult save(PropertyModel dto);

  void update(Long userId, Long propertyId, PropertyModel dto);

  List<RecentPropertyResult> findTopByUserId(Long userId, int size);
}
