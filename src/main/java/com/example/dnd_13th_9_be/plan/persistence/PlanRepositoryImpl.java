package com.example.dnd_13th_9_be.plan.persistence;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.example.dnd_13th_9_be.folder.persistence.QFolderEntity;
import com.example.dnd_13th_9_be.plan.persistence.dto.PlanSummary;
import com.example.dnd_13th_9_be.plan.persistence.entity.QPlanEntity;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

@RequiredArgsConstructor
public class PlanRepositoryImpl implements PlanRepositoryCustom {

  private final JPAQueryFactory query;

  @Override
  public List<PlanSummary> findSummariesByUserId(Long userId) {
    var plan = QPlanEntity.planEntity;
    var folder = QFolderEntity.folderEntity;

    return query
        .select(
            Projections.constructor(
                PlanSummary.class,
                plan.id,
                plan.name,
                plan.createdAt,
                folder.id.countDistinct(),
                plan.isDefault))
        .from(plan)
        .leftJoin(plan.folders, folder)
        .where(plan.user.id.eq(userId))
        .groupBy(plan.id, plan.name, plan.createdAt)
        .orderBy(plan.createdAt.desc())
        .fetch();
  }

  @Override
  public boolean rename(Long planId, String newName) {
    var plan = QPlanEntity.planEntity;
    long affected = query.update(plan).set(plan.name, newName).where(plan.id.eq(planId)).execute();
    return affected == 1L;
  }

  @Override
  @Transactional
  public boolean deleteByIdIfExists(Long planId) {
    var folder = QFolderEntity.folderEntity;

    query.delete(folder).where(folder.plan.id.eq(planId)).execute();

    var plan = QPlanEntity.planEntity;
    long affected = query.delete(plan).where(plan.id.eq(planId)).execute();
    return affected == 1L;
  }

  @Override
  public Long countByUserId(Long userId) {
    var plan = QPlanEntity.planEntity;
    return query.select(plan.id.count()).from(plan).where(plan.user.id.eq(userId)).fetchOne();
  }
}
