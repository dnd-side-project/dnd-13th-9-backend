package com.example.dnd_13th_9_be.property.persistence;

import com.example.dnd_13th_9_be.checklist.persistence.entity.ChecklistCategory;
import com.example.dnd_13th_9_be.property.application.dto.PropertyCategoryMemoDto;
import com.example.dnd_13th_9_be.property.application.repository.PropertyCategoryMemoRepository;
import com.example.dnd_13th_9_be.property.persistence.entity.Property;
import com.example.dnd_13th_9_be.property.persistence.entity.PropertyCategoryMemo;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PropertyCategoryMemoRepositoryImpl implements PropertyCategoryMemoRepository {
    private final EntityManager em;
    private final JpaPropertyCategoryMemoRepository jpaPropertyCategoryMemoRepository;

    @Override
    public void save(PropertyCategoryMemoDto dto) {
        ChecklistCategory category = em.getReference(ChecklistCategory.class, dto.categoryId());
        Property property = em.getReference(Property.class, dto.propertyId());
        PropertyCategoryMemo propertyCategoryMemo = PropertyCategoryMemo.builder()
            .category(category)
            .property(property)
            .memo(dto.memo())
            .build();
        jpaPropertyCategoryMemoRepository.save(propertyCategoryMemo);
    }
}
