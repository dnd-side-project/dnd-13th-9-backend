package com.example.dnd_13th_9_be.placeMemo.application.repository;

import com.example.dnd_13th_9_be.folder.persistence.entity.Folder;
import com.example.dnd_13th_9_be.placeMemo.application.model.PlaceMemoModel;
import com.example.dnd_13th_9_be.placeMemo.persistence.PlaceMemo;
import com.example.dnd_13th_9_be.user.persistence.User;
import org.springframework.security.core.parameters.P;

import java.util.List;
import java.util.Optional;

public interface PlaceMemoRepository {

    PlaceMemoModel save(Long userId, PlaceMemoModel placeMemoModel);

    Optional<PlaceMemoModel> findByIdAndUserId(Long placeMemoId, Long userId);

    void deleteById(Long placeMemoId, Long UserId);

    List<PlaceMemoModel> findByFolderIdAndUserId(Long folderId, Long userId);

    void update(Long userId, Long placeMemoId, PlaceMemoModel model);
}
