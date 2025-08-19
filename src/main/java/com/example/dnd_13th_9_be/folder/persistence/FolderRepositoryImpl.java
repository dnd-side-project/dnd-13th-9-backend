package com.example.dnd_13th_9_be.folder.persistence;

import java.util.List;
import jakarta.persistence.EntityManager;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import com.example.dnd_13th_9_be.folder.application.dto.FolderDetailResult;
import com.example.dnd_13th_9_be.folder.application.dto.FolderSummaryResult;
import com.example.dnd_13th_9_be.folder.application.repository.FolderRepository;
import com.example.dnd_13th_9_be.folder.persistence.entity.Folder;
import com.example.dnd_13th_9_be.global.error.BusinessException;
import com.example.dnd_13th_9_be.plan.persistence.entity.Plan;
import com.example.dnd_13th_9_be.user.persistence.User;

import static com.example.dnd_13th_9_be.global.error.ErrorCode.NOT_FOUND_FOLDER;

@Component
@RequiredArgsConstructor
public class FolderRepositoryImpl implements FolderRepository {
  private final EntityManager em;
  private final JpaFolderRepository jpaFolderRepository;

  @Override
  public FolderDetailResult create(Long userId, Long planId, String name, boolean isDefault) {
    Plan planRef = em.getReference(Plan.class, planId);
    User userRef = em.getReference(User.class, userId);
    Folder folder = Folder.of(userRef, planRef, name, isDefault);
    jpaFolderRepository.save(folder);
    return new FolderDetailResult(
        folder.getId(),
        folder.getName(),
        folder.getCreatedAt().toLocalDateTime(),
        folder.getIsDefault());
  }

  @Override
  public boolean rename(Long userId, Long folderId, String newName) {
    return jpaFolderRepository.rename(userId, folderId, newName);
  }

  @Override
  public boolean delete(Long userId, Long folderId) {
    return jpaFolderRepository.deleteByIdIfExists(userId, folderId);
  }

  @Override
  public List<FolderSummaryResult> findSummariesByPlanId(Long planId) {
    return jpaFolderRepository.findSummariesByPlanId(planId).stream()
        .map(
            r ->
                new FolderSummaryResult(
                    r.id(),
                    r.name(),
                    r.createdAt(),
                    r.locationRecordCount() + r.propertyRecordCount(),
                    r.isDefault()))
        .toList();
  }

  @Override
  public long countByPlanId(Long planId) {
    return jpaFolderRepository.countByPlanId(planId);
  }

  @Override
  public void verifyById(Long folderId) {
    var isNotExist = !jpaFolderRepository.existsById(folderId);
    if (isNotExist) {
      throw new BusinessException(NOT_FOUND_FOLDER);
    }
  }

  @Override
  public FolderDetailResult findById(Long folderId) {
    Folder entity =
        jpaFolderRepository
            .findById(folderId)
            .orElseThrow(() -> new BusinessException(NOT_FOUND_FOLDER));
    return new FolderDetailResult(
        entity.getId(),
        entity.getName(),
        entity.getCreatedAt().toLocalDateTime(),
        entity.getIsDefault());
  }
}
