package com.example.dnd_13th_9_be.property.application.repository;

import com.example.dnd_13th_9_be.property.application.dto.PropertyDto;
import com.example.dnd_13th_9_be.property.persistence.dto.PropertyResult;
import java.util.List;

import com.example.dnd_13th_9_be.property.application.model.PropertyImageModel;
import com.example.dnd_13th_9_be.property.application.model.PropertyModel;

public interface PropertyRepository {
  PropertyModel save(PropertyModel propertyModel);

  void delete(Long userId, Long propertyId);

  PropertyModel findDetailById(Long propertyId);

  PropertyResult save(PropertyDto dto);
}
