package com.example.dnd_13th_9_be.property.presentation.dto.response;

import com.example.dnd_13th_9_be.property.persistence.dto.PropertyImageResult;
import lombok.Builder;

@Builder
public record PropertyImageResponse(Long imageId, String url, Integer order) {
  public static PropertyImageResponse from(PropertyImageResult result) {
    return PropertyImageResponse.builder()
        .imageId(result.imageId())
        .url(result.imageUrl())
        .order(result.imageOrder())
        .build();
  }
}
