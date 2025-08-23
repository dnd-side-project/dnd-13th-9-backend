package com.example.dnd_13th_9_be.property.application.model.converter;

import org.springframework.stereotype.Component;

import com.example.dnd_13th_9_be.common.support.converter.AbstractEntityConverter;
import com.example.dnd_13th_9_be.property.application.model.PropertyImageModel;
import com.example.dnd_13th_9_be.property.persistence.entity.PropertyImage;

@Component
public class PropertyImageConverter
    implements AbstractEntityConverter<PropertyImage, PropertyImageModel> {

  @Override
  public PropertyImageModel from(PropertyImage propertyImage) {
    return PropertyImageModel.builder()
        .propertyImageId(propertyImage.getId())
        .imageUrl(propertyImage.getImageUrl())
        .order(propertyImage.getImageOrder())
        .build();
  }

  @Override
  public PropertyImage toEntity(PropertyImageModel propertyImageModel) {
    return PropertyImage.builder()
        .imageUrl(propertyImageModel.imageUrl())
        .imageOrder(propertyImageModel.order())
        .build();
  }
}
