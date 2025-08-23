package com.example.dnd_13th_9_be.property.application.repository;

import com.example.dnd_13th_9_be.property.application.model.PropertyImageModel;
import java.util.List;

public interface PropertyImageRepository {
    List<PropertyImageModel> findAllByPropertyId(Long propertyId);
    void delete(Long imageId);
    void updateOrder(Long propertyId, Integer newOrder);
}
