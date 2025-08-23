package com.example.dnd_13th_9_be.property.persistence;

import java.util.List;
import jakarta.persistence.EntityManager;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import com.example.dnd_13th_9_be.folder.persistence.entity.Folder;
import com.example.dnd_13th_9_be.global.error.BusinessException;
import com.example.dnd_13th_9_be.property.application.dto.PropertyDto;
import com.example.dnd_13th_9_be.property.application.model.PropertyModel;
import com.example.dnd_13th_9_be.property.application.model.converter.PropertyConverter;
import com.example.dnd_13th_9_be.property.application.repository.PropertyRepository;
import com.example.dnd_13th_9_be.property.persistence.dto.PropertyResult;
import com.example.dnd_13th_9_be.property.persistence.entity.Property;

import static com.example.dnd_13th_9_be.global.error.ErrorCode.NOT_FOUND_PROPERTY;

@Component
@RequiredArgsConstructor
public class PropertyRepositoryImpl implements PropertyRepository {
  private final EntityManager em;
  private final JpaPropertyRepository jpaPropertyRepository;
  private final PropertyConverter propertyConverter;

  @Override
  public void delete(Long userId, Long propertyId) {
    jpaPropertyRepository.deleteById(propertyId);
  }

  @Override
  public PropertyResult findById(Long propertyId) {
    Property property = jpaPropertyRepository.findByIdWithFolder(propertyId).orElseThrow(() -> new BusinessException(NOT_FOUND_PROPERTY));
    return PropertyResult.from(property);
  }

  @Override
  public PropertyResult save(PropertyDto dto) {
    Folder folder = em.getReference(Folder.class, dto.folderId());
    Property property =
        Property.builder()
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
