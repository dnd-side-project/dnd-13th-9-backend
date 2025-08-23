package com.example.dnd_13th_9_be.property.persistence;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.dnd_13th_9_be.property.persistence.entity.PropertyCategoryMemo;

public interface JpaPropertyCategoryMemoRepository
    extends JpaRepository<PropertyCategoryMemo, Long> {


}
