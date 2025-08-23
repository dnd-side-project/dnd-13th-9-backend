package com.example.dnd_13th_9_be.property.application.repository;

import java.util.Set;

public interface PropertyRequiredCheckRepository {
  void save(Long itemId, Long propertyId);

  Set<Long> findAllIdsByPropertyId(Long propertyId);
}
