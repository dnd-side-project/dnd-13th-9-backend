package com.example.dnd_13th_9_be.folder.application.port;

import com.example.dnd_13th_9_be.folder.application.dto.FolderDetailResult;

public interface FolderCommandPort {
  FolderDetailResult create(Long planId, String name, boolean isDefault);

  boolean rename(Long folderId, String newName);

  boolean delete(Long folderId);
}
