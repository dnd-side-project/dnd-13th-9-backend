package com.example.dnd_13th_9_be.folder.persistence.dto;

import java.time.LocalDateTime;

public record FolderSummary(
    Long id,
    String name,
    LocalDateTime createdAt,
    long locationRecordCount,
    long propertyRecordCount,
    boolean isDefault) {}
