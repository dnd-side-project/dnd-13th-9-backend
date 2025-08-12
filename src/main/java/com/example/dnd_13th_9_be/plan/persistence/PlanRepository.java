package com.example.dnd_13th_9_be.plan.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.dnd_13th_9_be.plan.persistence.entity.PlanEntity;

@Repository
public interface PlanRepository extends JpaRepository<PlanEntity, Long>, PlanRepositoryCustom {}
