package com.example.dnd_13th_9_be.folder.persistence;

import java.util.List;

import lombok.RequiredArgsConstructor;

import com.example.dnd_13th_9_be.folder.persistence.dto.FolderSummary;
import com.example.dnd_13th_9_be.location.persistence.QLocationRecordEntity;
import com.example.dnd_13th_9_be.property.persistence.QPropertyRecordEntity;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

@RequiredArgsConstructor
public class FolderRepositoryImpl implements FolderRepositoryCustom {
  private final JPAQueryFactory query;

  @Override
  public List<FolderSummary> findSummariesByPlanId(Long planId) {
    var folder = QFolderEntity.folderEntity;
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
                propertyCnt))
        .from(folder)
        .where(folder.plan.id.eq(planId))
        .orderBy(folder.createdAt.desc())
        .fetch();
  }
}
