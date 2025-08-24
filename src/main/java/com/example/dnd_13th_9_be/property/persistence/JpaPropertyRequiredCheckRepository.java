package com.example.dnd_13th_9_be.property.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.dnd_13th_9_be.property.persistence.entity.PropertyRequiredCheck;

public interface JpaPropertyRequiredCheckRepository
    extends JpaRepository<PropertyRequiredCheck, Long> {}
