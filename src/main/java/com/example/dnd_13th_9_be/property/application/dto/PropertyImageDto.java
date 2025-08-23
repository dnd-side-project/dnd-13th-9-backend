package com.example.dnd_13th_9_be.property.application.dto;

import lombok.Builder;

@Builder
public record PropertyImageDto(Long propertyId, String imageUrl, Integer order) {}
