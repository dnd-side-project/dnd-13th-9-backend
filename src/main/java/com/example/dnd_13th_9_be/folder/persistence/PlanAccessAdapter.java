package com.example.dnd_13th_9_be.folder.persistence;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import com.example.dnd_13th_9_be.folder.application.port.PlanAccessPort;
import com.example.dnd_13th_9_be.plan.persistence.QPlanEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Component
@RequiredArgsConstructor
public class PlanAccessAdapter implements PlanAccessPort {
  private final JPAQueryFactory query;

  @Override
  public boolean existById(Long planId) {
    var plan = QPlanEntity.planEntity;

    return query.selectOne().from(plan).where(plan.id.eq(planId)).fetchFirst() != null;
  }
}
