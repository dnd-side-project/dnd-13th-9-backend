package com.example.dnd_13th_9_be.property.application.model;

import com.example.dnd_13th_9_be.common.support.AbstractModel;
import lombok.Builder;
@Builder
public record PropertyImageModel(
    Long propertyImageId,
    String imageUrl,
    Integer sort
) implements AbstractModel {

}
