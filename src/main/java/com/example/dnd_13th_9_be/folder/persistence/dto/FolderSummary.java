package com.example.dnd_13th_9_be.folder.persistence.dto;

import java.sql.Timestamp;

public record FolderSummary(
    Long id,
    String name,
    Timestamp createdAt,
    long locationRecordCount,
    long propertyRecordCount,
    boolean isDefault) {}
