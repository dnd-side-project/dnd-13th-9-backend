package com.example.dnd_13th_9_be.property.persistence;

import com.example.dnd_13th_9_be.checklist.application.model.UserRequiredItemModel;
import com.example.dnd_13th_9_be.checklist.persistence.entity.ChecklistItem;
import com.example.dnd_13th_9_be.property.application.repository.PropertyRequiredCheckRepository;
import com.example.dnd_13th_9_be.property.persistence.entity.Property;
import com.example.dnd_13th_9_be.property.persistence.entity.PropertyRequiredCheck;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PropertyRequiredCheckRepositoryImpl implements PropertyRequiredCheckRepository {
    private final EntityManager em;

    private final JpaPropertyRequiredCheckRepository jpaPropertyRequiredCheckRepository;
    @Override
    public void save(Long itemId, Long propertyId) {
        ChecklistItem checklistItem = em.getReference(ChecklistItem.class, itemId);
        Property property = em.getReference(Property.class, propertyId);
        PropertyRequiredCheck propertyRequiredCheck = PropertyRequiredCheck.builder()
            .checklistItem(checklistItem)
            .property(property)
            .build();
        jpaPropertyRequiredCheckRepository.save(propertyRequiredCheck);
    }
}
