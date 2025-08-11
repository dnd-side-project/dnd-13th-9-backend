package com.example.dnd_13th_9_be.plan.persistence;

import java.util.List;
import lombok.RequiredArgsConstructor;

import com.example.dnd_13th_9_be.folder.persistence.QFolderEntity;
import com.example.dnd_13th_9_be.plan.persistence.dto.PlanSummary;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

@RequiredArgsConstructor
public class PlanRepositoryImpl implements PlanRepositoryCustom {

  private final JPAQueryFactory query;

  @Override
  public List<PlanSummary> findSummariesByUserId(Long userId) {
    final QPlanEntity planEntity = QPlanEntity.planEntity;
    final QFolderEntity folderEntity = QFolderEntity.folderEntity;
    return query
        .select(
            Projections.constructor(
                PlanSummary.class,
                planEntity.id,
                planEntity.name,
                planEntity.createdAt,
                folderEntity.id.countDistinct()))
        .from(planEntity)
        .leftJoin(planEntity.folders, folderEntity)
        .where(planEntity.user.id.eq(userId))
        .groupBy(planEntity.id, planEntity.name, planEntity.createdAt)
        .orderBy(planEntity.createdAt.desc())
        .fetch();
  }
}
