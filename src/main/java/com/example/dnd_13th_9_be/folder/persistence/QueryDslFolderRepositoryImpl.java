package com.example.dnd_13th_9_be.folder.persistence;

import java.util.List;

import lombok.RequiredArgsConstructor;

import com.example.dnd_13th_9_be.folder.persistence.dto.FolderSummary;
import com.example.dnd_13th_9_be.folder.persistence.entity.QFolder;
import com.example.dnd_13th_9_be.location.persistence.QLocationRecordEntity;
import com.example.dnd_13th_9_be.property.persistence.QPropertyRecordEntity;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

@RequiredArgsConstructor
public class QueryDslFolderRepositoryImpl implements QueryDslFolderRepository {
  private final JPAQueryFactory query;

  @Override
  public List<FolderSummary> findSummariesByPlanId(Long planId) {
    var folder = QFolder.folder;
    var location = QLocationRecordEntity.locationRecordEntity;
    var property = QPropertyRecordEntity.propertyRecordEntity;

    var locationCnt =
        JPAExpressions.select(location.id.count())
            .from(location)
            .where(location.folder.id.eq(folder.id));

    var propertyCnt =
        JPAExpressions.select(property.id.count())
            .from(property)
            .where(property.folder.id.eq(folder.id));

    return query
        .select(
            Projections.constructor(
                FolderSummary.class,
                folder.id,
                folder.name,
                folder.createdAt,
                locationCnt,
                propertyCnt,
                folder.isDefault))
        .from(folder)
        .where(folder.plan.id.eq(planId))
        .orderBy(folder.createdAt.desc())
        .fetch();
  }

  @Override
  public boolean rename(Long userId, Long folderId, String newName) {
    var folder = QFolder.folder;
    long affected =
        query
            .update(folder)
            .set(folder.name, newName)
            .where(folder.id.eq(folderId).and(folder.user.id.eq(userId)))
            .execute();
    return affected == 1L;
  }

  @Override
  public boolean deleteByIdIfExists(Long userId, Long folderId) {
    var folder = QFolder.folder;
    var affected =
        query.delete(folder).where(folder.id.eq(folderId).and(folder.user.id.eq(userId))).execute();
    return affected == 1L;
  }

  @Override
  public Long countByPlanId(Long planId) {
    var folder = QFolder.folder;
    return query.select(folder.id.count()).from(folder).where(folder.plan.id.eq(planId)).fetchOne();
  }
}
