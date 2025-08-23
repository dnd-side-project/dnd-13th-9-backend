package com.example.dnd_13th_9_be.property.presentation.dto.response;

import com.example.dnd_13th_9_be.property.application.model.PropertyImageModel;
import lombok.Builder;

@Builder
public record PropertyImageResponse(
    Long imageId,
    String url,
    Integer order
) {
    public static PropertyImageResponse of(PropertyImageModel m) {
        return PropertyImageResponse.builder()
            .imageId(m.propertyImageId())
            .url(m.imageUrl())
            .order(m.order())
            .build();
    }
}
