package com.example.dnd_13th_9_be.property.persistence;

import com.example.dnd_13th_9_be.property.application.dto.PropertyDto;
import com.example.dnd_13th_9_be.property.persistence.dto.PropertyResult;
import java.util.List;
import jakarta.persistence.EntityManager;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import com.example.dnd_13th_9_be.checklist.persistence.entity.ChecklistCategory;
import com.example.dnd_13th_9_be.checklist.persistence.entity.ChecklistItem;
import com.example.dnd_13th_9_be.folder.persistence.entity.Folder;
import com.example.dnd_13th_9_be.global.error.BusinessException;
import com.example.dnd_13th_9_be.property.application.model.PropertyImageModel;
import com.example.dnd_13th_9_be.property.application.model.PropertyModel;
import com.example.dnd_13th_9_be.property.application.model.converter.PropertyConverter;
import com.example.dnd_13th_9_be.property.application.model.converter.PropertyImageConverter;
import com.example.dnd_13th_9_be.property.application.repository.PropertyRepository;
import com.example.dnd_13th_9_be.property.persistence.entity.Property;
import com.example.dnd_13th_9_be.property.persistence.entity.PropertyCategoryMemo;
import com.example.dnd_13th_9_be.property.persistence.entity.PropertyImage;

import static com.example.dnd_13th_9_be.global.error.ErrorCode.NOT_FOUND_PROPERTY;

@Component
@RequiredArgsConstructor
public class PropertyRepositoryImpl implements PropertyRepository {
  private final EntityManager em;
  private final JpaPropertyRepository jpaPropertyRepository;
  private final JpaPropertyImageRepository jpaPropertyImageRepository;
  private final PropertyConverter propertyConverter;
  private final PropertyImageConverter propertyImageConverter;

  @Override
  public PropertyModel save(PropertyModel model) {
    Folder folder = em.getReference(Folder.class, model.folderId());
    List<ChecklistItem> requiredCheckList =
        model.requiredChecklist().stream()
            .map(m -> em.getReference(ChecklistItem.class, m.checklistId()))
            .toList();
    List<PropertyCategoryMemo> categoryMemoList =
        model.categoryMemo().stream()
            .map(
                m ->
                    PropertyCategoryMemo.builder()
                        .category(em.getReference(ChecklistCategory.class, m.categoryId()))
                        .memo(m.memo())
                        .build())
            .toList();
    Property property =
        propertyConverter.toEntity(folder, model, requiredCheckList, categoryMemoList);
    Property savedProperty = jpaPropertyRepository.save(property);
    return propertyConverter.from(savedProperty);
  }

  @Override
  public void delete(Long userId, Long propertyId) {
    jpaPropertyRepository.deleteById(propertyId);
  }

  @Override
  public PropertyModel findDetailById(Long propertyId) {
    Property property =
        jpaPropertyRepository
            .findDetailById(propertyId)
            .orElseThrow(() -> new BusinessException(NOT_FOUND_PROPERTY));
    return propertyConverter.from(property);
  }

  @Override
  public PropertyResult save(PropertyDto dto) {
    Folder folder = em.getReference(Folder.class, dto.folderId());
    Property property = Property.builder()
        .folder(folder)
        .title(dto.title())
        .feeling(dto.feeling())
        .memo(dto.memo())
        .referenceUrl(dto.referenceUrl())
        .address(dto.address())
        .detailAddress(dto.detailAddress())
        .latitude(dto.latitude())
        .longitude(dto.longitude())
        .contractType(dto.contractType())
        .houseType(dto.houseType())
        .depositBig(dto.depositBig())
        .depositSmall(dto.depositSmall())
        .managementFee(dto.managementFee())
        .moveInInfo(dto.moveInInfo())
        .requiredCheckMemo(dto.requiredCheckMemo())
        .build();
    Property savedProperty = jpaPropertyRepository.save(property);
    return PropertyResult.from(savedProperty);
  }
}
