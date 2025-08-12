package com.example.dnd_13th_9_be.folder.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FolderRepository
    extends JpaRepository<FolderEntity, Long>, FolderRepositoryCustom {}
