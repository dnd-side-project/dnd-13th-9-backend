package com.example.dnd_13th_9_be.property.persistence;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import jakarta.persistence.EntityManager;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.example.dnd_13th_9_be.checklist.persistence.entity.ChecklistCategory;
import com.example.dnd_13th_9_be.checklist.persistence.entity.QChecklistCategory;
import com.example.dnd_13th_9_be.property.application.dto.PropertyCategoryMemoDto;
import com.example.dnd_13th_9_be.property.application.repository.PropertyCategoryMemoRepository;
import com.example.dnd_13th_9_be.property.persistence.dto.PropertyCategoryMemoResult;
import com.example.dnd_13th_9_be.property.persistence.entity.Property;
import com.example.dnd_13th_9_be.property.persistence.entity.PropertyCategoryMemo;
import com.example.dnd_13th_9_be.property.persistence.entity.QPropertyCategoryMemo;
import com.querydsl.jpa.impl.JPAQueryFactory;

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
  public List<PropertyCategoryMemoResult> findAllByPropertyId(Long propertyId) {
    var categoryMemo = QPropertyCategoryMemo.propertyCategoryMemo;
    var category = QChecklistCategory.checklistCategory;

    List<PropertyCategoryMemo> propertyCategoryMemo =
        query
            .selectFrom(categoryMemo)
            .join(categoryMemo.category, category)
            .fetchJoin()
            .where(categoryMemo.property.id.eq(propertyId))
            .fetch();

    return propertyCategoryMemo.stream().map(PropertyCategoryMemoResult::from).toList();
  }

  @Override
  public Set<Long> findAllIdByPropertyId(Long propertyId) {
    var categoryMemo = QPropertyCategoryMemo.propertyCategoryMemo;

    return new HashSet<>(
        query
            .select(categoryMemo.id)
            .from(categoryMemo)
            .where(categoryMemo.property.id.eq(propertyId))
            .fetch());
  }

  @Override
  public void update(PropertyCategoryMemoDto dto) {
    var categoryMemo = QPropertyCategoryMemo.propertyCategoryMemo;

    PropertyCategoryMemo memo =
        query
            .selectFrom(categoryMemo)
            .where(
                categoryMemo
                    .category
                    .id
                    .eq(dto.categoryId())
                    .and(categoryMemo.property.id.eq(dto.propertyId())))
            .fetchOne();

    if (memo == null) {
      save(dto);
    } else {
      if (dto.memo().equals(memo.getMemo())) {
        memo.updateMemo(dto.memo());
        jpaPropertyCategoryMemoRepository.save(memo);
      }
    }
  }

  @Override
  @Transactional
  public void deleteAllByPropertyId(Long propertyId) {
    jpaPropertyCategoryMemoRepository.deleteAllByPropertyId(propertyId);
  }
}
