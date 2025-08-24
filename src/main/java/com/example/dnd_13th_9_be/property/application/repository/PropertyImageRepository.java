package com.example.dnd_13th_9_be.property.application.repository;

import java.util.List;

import com.example.dnd_13th_9_be.property.application.dto.PropertyImageDto;
import com.example.dnd_13th_9_be.property.persistence.dto.PropertyImageResult;

public interface PropertyImageRepository {
  void save(PropertyImageDto model);

  void verifyByIdAndPropertyId(Long imageId, Long propertyId);

  List<PropertyImageResult> findAllByPropertyId(Long propertyId);

  void delete(Long imageId, Long propertyId);

  void updateOrder(Long imageId, Integer newOrder);
}
