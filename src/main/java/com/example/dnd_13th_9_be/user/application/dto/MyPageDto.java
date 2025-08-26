package com.example.dnd_13th_9_be.user.application.dto;

import lombok.Builder;

@Builder
public record MyPageDto(String name, long propertyCount, long checklistCount) {}
