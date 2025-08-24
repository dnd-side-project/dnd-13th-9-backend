package com.example.dnd_13th_9_be.property.application.model;

import lombok.Builder;

@Builder
public record PropertyImageModel(Long propertyId, String imageUrl, Integer order) {}
