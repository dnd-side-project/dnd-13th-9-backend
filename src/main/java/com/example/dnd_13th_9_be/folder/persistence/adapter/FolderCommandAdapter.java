package com.example.dnd_13th_9_be.folder.persistence.adapter;

import com.example.dnd_13th_9_be.folder.application.port.FolderCommandPort;
import com.example.dnd_13th_9_be.folder.application.dto.FolderDetailResult;
import com.example.dnd_13th_9_be.folder.persistence.FolderEntity;
import com.example.dnd_13th_9_be.folder.persistence.FolderRepository;
import com.example.dnd_13th_9_be.plan.persistence.entity.PlanEntity;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FolderCommandAdapter implements FolderCommandPort {
    private final EntityManager em;
    private final FolderRepository folderRepository;

    @Override
    public FolderDetailResult create(Long planId, String name, boolean isDefault) {
        PlanEntity planRef = em.getReference(PlanEntity.class, planId);
        FolderEntity folderEntity = FolderEntity.of(planRef, name, isDefault);
        folderRepository.save(folderEntity);
        return new FolderDetailResult(
            folderEntity.getId(),
            folderEntity.getName(),
            folderEntity.getCreatedAt(),
            folderEntity.isDefault()
        );
    }

    @Override
    public boolean rename(Long folderId, String newName) {
        return folderRepository.rename(folderId, newName);
    }

    @Override
    public boolean delete(Long folderId) {
        return folderRepository.delete(folderId);
    }
}
