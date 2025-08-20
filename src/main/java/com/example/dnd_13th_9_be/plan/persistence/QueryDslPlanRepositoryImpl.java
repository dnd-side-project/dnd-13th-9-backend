package com.example.dnd_13th_9_be.plan.persistence;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.example.dnd_13th_9_be.folder.persistence.entity.QFolder;
import com.example.dnd_13th_9_be.plan.persistence.dto.PlanSummary;
import com.example.dnd_13th_9_be.plan.persistence.entity.QPlan;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

@RequiredArgsConstructor
public class QueryDslPlanRepositoryImpl implements QueryDslPlanRepository {

  private final JPAQueryFactory query;

  @Override
  public List<PlanSummary> findSummariesByUserId(Long userId) {
    var plan = QPlan.plan;
    var folder = QFolder.folder;

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
        .groupBy(plan.id, plan.name, plan.createdAt, plan.isDefault)
        .orderBy(plan.createdAt.desc())
        .fetch();
  }

  @Override
  public boolean rename(Long userId, Long planId, String newName) {
    var plan = QPlan.plan;
    long affected =
        query
            .update(plan)
            .set(plan.name, newName)
            .where(plan.id.eq(planId).and(plan.user.id.eq(userId)))
            .execute();
    return affected == 1L;
  }

  @Override
  @Transactional
  public boolean deleteByIdIfExists(Long userId, Long planId) {
    var folder = QFolder.folder;

    query.delete(folder).where(folder.plan.id.eq(planId).and(folder.user.id.eq(userId))).execute();

    var plan = QPlan.plan;
    long affected =
        query.delete(plan).where(plan.id.eq(planId).and(plan.user.id.eq(userId))).execute();
    return affected == 1L;
  }

  @Override
  public Long countByUserId(Long userId) {
    var plan = QPlan.plan;
    return query.select(plan.id.count()).from(plan).where(plan.user.id.eq(userId)).fetchOne();
  }

  @Override
  public boolean verifyExistsById(Long userId, Long planId) {
    var plan = QPlan.plan;
    return query
            .selectOne()
            .from(plan)
            .where(plan.user.id.eq(userId).and(plan.id.eq(planId)))
            .fetchFirst()
        != null;
  }
}
