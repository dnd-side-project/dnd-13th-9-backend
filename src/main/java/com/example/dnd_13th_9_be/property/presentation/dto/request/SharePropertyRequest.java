package com.example.dnd_13th_9_be.property.presentation.dto.request;

import lombok.Builder;

@Builder
public record SharePropertyRequest(Long userId, Long propertyId) {}
