package com.example.dnd_13th_9_be.property.application.model;

import com.example.dnd_13th_9_be.common.support.AbstractModel;
import lombok.Builder;

@Builder
public record PropertyRequiredCheckModel(
    Long propertyRequiredCheckId,
    Long checklistId
) implements AbstractModel { }
