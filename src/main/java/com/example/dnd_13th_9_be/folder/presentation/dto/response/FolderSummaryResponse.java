package com.example.dnd_13th_9_be.folder.presentation.dto.response;

import java.time.LocalDateTime;

public record FolderSummaryResponse(
    Long folderId, String name, LocalDateTime createdAt, long recordCount) {}
