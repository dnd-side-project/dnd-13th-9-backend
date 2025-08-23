package com.example.dnd_13th_9_be.property.persistence.dto;

import com.example.dnd_13th_9_be.property.persistence.entity.PropertyImage;
import lombok.Builder;

@Builder
public record PropertyImageResult(
    Long imageId,
    String imageUrl,
    Integer imageOrder
) {
    public static PropertyImageResult from(PropertyImage entity) {
        return PropertyImageResult.builder()
            .imageId(entity.getId())
            .imageUrl(entity.getImageUrl())
            .imageOrder(entity.getImageOrder())
            .build();
    }
}
