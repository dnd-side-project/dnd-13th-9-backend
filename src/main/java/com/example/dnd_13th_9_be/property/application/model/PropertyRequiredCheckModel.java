package com.example.dnd_13th_9_be.property.application.model;

import lombok.Builder;

import com.example.dnd_13th_9_be.common.support.AbstractModel;

@Builder
public record PropertyRequiredCheckModel(Long propertyRequiredCheckId, Long checklistId)
    implements AbstractModel {}
