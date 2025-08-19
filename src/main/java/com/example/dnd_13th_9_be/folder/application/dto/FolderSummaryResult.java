package com.example.dnd_13th_9_be.folder.application.dto;

import java.sql.Timestamp;

public record FolderSummaryResult(
    Long folderId, String name, Timestamp createdAt, long recordCount, boolean isDefaultFolder) {}
