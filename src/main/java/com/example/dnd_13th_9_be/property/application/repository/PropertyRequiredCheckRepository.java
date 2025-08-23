package com.example.dnd_13th_9_be.property.application.repository;

import com.example.dnd_13th_9_be.checklist.application.model.UserRequiredItemModel;

public interface PropertyRequiredCheckRepository {
    void save(Long itemId, Long propertyId);
}
