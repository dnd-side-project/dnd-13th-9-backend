package com.example.dnd_13th_9_be.folder.presentation.dto.response;

import java.sql.Timestamp;

public record FolderSummaryResponse(
    Long folderId, String name, Timestamp createdAt, long recordCount, boolean isDefaultFolder) {}
