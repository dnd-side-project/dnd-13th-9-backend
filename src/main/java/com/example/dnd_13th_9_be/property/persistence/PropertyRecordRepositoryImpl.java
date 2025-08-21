package com.example.dnd_13th_9_be.property.persistence;

import com.example.dnd_13th_9_be.checklist.persistence.entity.ChecklistCategory;
import com.example.dnd_13th_9_be.checklist.persistence.entity.ChecklistItem;
import com.example.dnd_13th_9_be.folder.persistence.entity.Folder;
import com.example.dnd_13th_9_be.plan.persistence.entity.Plan;
import com.example.dnd_13th_9_be.property.application.model.PropertyRecordModel;
import com.example.dnd_13th_9_be.property.application.model.converter.PropertyRecordConverter;
import com.example.dnd_13th_9_be.property.application.repository.PropertyRecordRepository;
import com.example.dnd_13th_9_be.property.persistence.entity.PropertyCategoryMemo;
import com.example.dnd_13th_9_be.property.persistence.entity.PropertyRecord;
import jakarta.persistence.EntityManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PropertyRecordRepositoryImpl implements PropertyRecordRepository {
    private final EntityManager em;
    private final JpaPropertyRecordRepository jpaPropertyRecordRepository;
    private final PropertyRecordConverter propertyRecordConverter;

    @Override
    public PropertyRecordModel save(PropertyRecordModel model) {
        Plan plan = em.getReference(Plan.class, model.planId());
        Folder folder = em.getReference(Folder.class, model.folderId());
        List<ChecklistItem> requiredCheckList = model.requiredChecklist().stream().map(
            m -> em.getReference(ChecklistItem.class, m.checklistId())
        ).toList();
        List<PropertyCategoryMemo> categoryMemoList = model.categoryMemo().stream().map(
            m -> PropertyCategoryMemo.builder()
                .category(em.getReference(ChecklistCategory.class, m.categoryId()))
                .memo(m.memo())
                .build()
        ).toList();
        PropertyRecord propertyRecord = propertyRecordConverter.toEntity(plan, folder, model, requiredCheckList, categoryMemoList);
        PropertyRecord savedPropertyRecord = jpaPropertyRecordRepository.save(propertyRecord);
        return propertyRecordConverter.from(savedPropertyRecord);
    }
}
