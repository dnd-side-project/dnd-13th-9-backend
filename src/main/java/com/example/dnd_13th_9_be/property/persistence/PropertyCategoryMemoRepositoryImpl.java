package com.example.dnd_13th_9_be.property.persistence;

import com.example.dnd_13th_9_be.checklist.persistence.entity.QChecklistCategory;
import com.example.dnd_13th_9_be.property.persistence.dto.PropertyCategoryMemoResult;
import com.example.dnd_13th_9_be.property.persistence.entity.QPropertyCategoryMemo;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.List;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import com.example.dnd_13th_9_be.checklist.persistence.entity.ChecklistCategory;
import com.example.dnd_13th_9_be.property.application.dto.PropertyCategoryMemoDto;
import com.example.dnd_13th_9_be.property.application.repository.PropertyCategoryMemoRepository;
import com.example.dnd_13th_9_be.property.persistence.entity.Property;
import com.example.dnd_13th_9_be.property.persistence.entity.PropertyCategoryMemo;

@Component
@RequiredArgsConstructor
public class PropertyCategoryMemoRepositoryImpl implements PropertyCategoryMemoRepository {
  private final EntityManager em;
  private final JPAQueryFactory query;
  private final JpaPropertyCategoryMemoRepository jpaPropertyCategoryMemoRepository;

  @Override
  public void save(PropertyCategoryMemoDto dto) {
    ChecklistCategory category = em.getReference(ChecklistCategory.class, dto.categoryId());
    Property property = em.getReference(Property.class, dto.propertyId());
    PropertyCategoryMemo propertyCategoryMemo =
        PropertyCategoryMemo.builder()
            .category(category)
            .property(property)
            .memo(dto.memo())
            .build();
    jpaPropertyCategoryMemoRepository.save(propertyCategoryMemo);
  }

  @Override
  public List<PropertyCategoryMemoResult> findAllById(Long propertyId) {
    var categoryMemo = QPropertyCategoryMemo.propertyCategoryMemo;
    var category = QChecklistCategory.checklistCategory;

    List<PropertyCategoryMemo> propertyCategoryMemo = query
        .selectFrom(categoryMemo)
        .join(categoryMemo.category, category).fetchJoin()
        .where(categoryMemo.property.id.eq(propertyId))
        .fetch();

    return propertyCategoryMemo.stream().map(PropertyCategoryMemoResult::from).toList();
  }


}
