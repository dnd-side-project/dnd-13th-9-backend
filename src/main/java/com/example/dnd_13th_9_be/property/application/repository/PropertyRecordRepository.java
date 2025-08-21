package com.example.dnd_13th_9_be.property.application.repository;

import com.example.dnd_13th_9_be.property.application.model.PropertyRecordModel;

public interface PropertyRecordRepository {
    PropertyRecordModel save(PropertyRecordModel propertyRecordModel);
    void delete(Long userId, Long propertyId);
}
