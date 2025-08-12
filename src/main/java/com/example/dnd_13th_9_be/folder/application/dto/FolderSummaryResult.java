package com.example.dnd_13th_9_be.folder.application.dto;

import java.time.LocalDateTime;

public record FolderSummaryResult(
    Long folderId, String name, LocalDateTime createdAt, long recordCount, boolean isDefaultFolder) {}
