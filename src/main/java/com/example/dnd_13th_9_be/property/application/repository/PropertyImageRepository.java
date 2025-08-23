package com.example.dnd_13th_9_be.property.application.repository;

import com.example.dnd_13th_9_be.property.persistence.dto.PropertyImageResult;
import java.util.List;

import com.example.dnd_13th_9_be.property.application.dto.PropertyImageDto;

public interface PropertyImageRepository {
  void save(PropertyImageDto model);

  List<PropertyImageResult> findAllByPropertyId(Long propertyId);

  void delete(Long imageId);

  void updateOrder(Long propertyId, Integer newOrder);
}
