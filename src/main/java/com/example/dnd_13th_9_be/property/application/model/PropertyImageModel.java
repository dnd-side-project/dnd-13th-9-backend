package com.example.dnd_13th_9_be.property.application.model;

import lombok.Builder;

import com.example.dnd_13th_9_be.common.support.AbstractModel;

@Builder
public record PropertyImageModel(Long propertyImageId, String imageUrl, Integer order)
    implements AbstractModel {
  public PropertyImageModel withOrder(Integer newOrder) {
    return new PropertyImageModel(this.propertyImageId, this.imageUrl, newOrder);
  }
}
