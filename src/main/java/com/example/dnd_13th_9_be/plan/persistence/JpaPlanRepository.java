package com.example.dnd_13th_9_be.plan.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.dnd_13th_9_be.plan.persistence.entity.Plan;

@Repository
public interface JpaPlanRepository extends JpaRepository<Plan, Long>, QueryDslPlanRepository {}
