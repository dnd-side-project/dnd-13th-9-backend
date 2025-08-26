package com.example.dnd_13th_9_be.placeMemo.application.repository;

import java.util.List;
import java.util.Optional;

import com.example.dnd_13th_9_be.placeMemo.application.model.PlaceMemoModel;

public interface PlaceMemoRepository {

  PlaceMemoModel save(Long userId, PlaceMemoModel placeMemoModel);

  Optional<PlaceMemoModel> findByIdAndUserId(Long placeMemoId, Long userId);

  void deleteById(Long placeMemoId, Long userId);

  List<PlaceMemoModel> findByFolderIdAndUserId(Long folderId, Long userId);

  void update(Long userId, Long placeMemoId, PlaceMemoModel model);
}
