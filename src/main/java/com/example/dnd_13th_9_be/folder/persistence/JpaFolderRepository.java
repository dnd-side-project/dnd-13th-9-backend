package com.example.dnd_13th_9_be.folder.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.dnd_13th_9_be.folder.persistence.entity.Folder;

@Repository
public interface JpaFolderRepository extends JpaRepository<Folder, Long>, QueryDslFolderRepository {
  Optional<Folder> findByIdAndUserId(Long folderId, Long userId);
  boolean existsByIdAndUserId(Long folderId, Long userId);
}
