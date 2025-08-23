package com.example.dnd_13th_9_be.property.application.repository;

import com.example.dnd_13th_9_be.property.application.dto.PropertyDto;
import com.example.dnd_13th_9_be.property.application.model.PropertyModel;
import com.example.dnd_13th_9_be.property.persistence.dto.PropertyResult;

public interface PropertyRepository {
//  PropertyModel save(PropertyModel propertyModel);

  void delete(Long userId, Long propertyId);

//  PropertyModel findDetailById(Long propertyId);

  PropertyResult findById(Long propertyId);

  PropertyResult save(PropertyDto dto);
}
