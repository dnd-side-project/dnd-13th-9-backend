package com.example.dnd_13th_9_be.property.persistence;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import jakarta.persistence.EntityManager;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import com.example.dnd_13th_9_be.checklist.persistence.entity.ChecklistItem;
import com.example.dnd_13th_9_be.checklist.persistence.entity.QChecklistItem;
import com.example.dnd_13th_9_be.property.application.repository.PropertyRequiredCheckRepository;
import com.example.dnd_13th_9_be.property.persistence.entity.Property;
import com.example.dnd_13th_9_be.property.persistence.entity.PropertyRequiredCheck;
import com.example.dnd_13th_9_be.property.persistence.entity.QPropertyRequiredCheck;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Component
@RequiredArgsConstructor
public class PropertyRequiredCheckRepositoryImpl implements PropertyRequiredCheckRepository {
  private final JPAQueryFactory query;
  private final EntityManager em;
  private final JpaPropertyRequiredCheckRepository jpaPropertyRequiredCheckRepository;

  @Override
  public void save(Long itemId, Long propertyId) {
    var requiredCheck = QPropertyRequiredCheck.propertyRequiredCheck;
    Long id = query.select(requiredCheck.id)
        .from(requiredCheck)
        .where(requiredCheck.checklistItem.id.eq(itemId).and(requiredCheck.property.id.eq(propertyId)))
        .fetchOne();

    if (id == null) {
    ChecklistItem checklistItem = em.getReference(ChecklistItem.class, itemId);
    Property property = em.getReference(Property.class, propertyId);

      PropertyRequiredCheck propertyRequiredCheck =
          PropertyRequiredCheck.builder().checklistItem(checklistItem).property(property).build();
      jpaPropertyRequiredCheckRepository.save(propertyRequiredCheck);
    }
  }

  @Override
  public Set<Long> findAllIdsByPropertyId(Long propertyId) {
    var requiredCheck = QPropertyRequiredCheck.propertyRequiredCheck;
    var checklistItem = QChecklistItem.checklistItem;
    List<Long> idList =
        query
            .select(checklistItem.id)
            .from(requiredCheck)
            .join(requiredCheck.checklistItem, checklistItem)
            .where(requiredCheck.property.id.eq(propertyId))
            .orderBy(checklistItem.id.asc())
            .distinct()
            .fetch();
    return new LinkedHashSet<>(idList);
  }
}
