package com.example.dnd_13th_9_be.placeMemo.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaPlaceMemoRepository extends JpaRepository<PlaceMemo, Long> {

    Optional<PlaceMemo> findByIdAndUserId(Long placeMemoId, Long userId);

    List<PlaceMemo> findByFolderIdAndUserIdOrderByIdDesc(Long folderId, Long userId);



}
