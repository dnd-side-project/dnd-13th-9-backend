package com.example.dnd_13th_9_be.property.persistence;

import java.util.List;
import jakarta.persistence.EntityManager;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.example.dnd_13th_9_be.global.error.BusinessException;
import com.example.dnd_13th_9_be.property.application.dto.PropertyImageDto;
import com.example.dnd_13th_9_be.property.application.repository.PropertyImageRepository;
import com.example.dnd_13th_9_be.property.persistence.dto.PropertyImageResult;
import com.example.dnd_13th_9_be.property.persistence.entity.Property;
import com.example.dnd_13th_9_be.property.persistence.entity.PropertyImage;

import static com.example.dnd_13th_9_be.global.error.ErrorCode.NOT_FOUND_PROPERTY_IMAGE;

@Component
@Slf4j
@RequiredArgsConstructor
public class PropertyImageRepositoryImpl implements PropertyImageRepository {
  private final EntityManager em;
  private final JpaPropertyImageRepository jpaPropertyImageRepository;

  @Override
  public void save(PropertyImageDto model) {
    Property property = em.getReference(Property.class, model.propertyId());
    PropertyImage image =
        PropertyImage.builder()
            .property(property)
            .imageUrl(model.imageUrl())
            .imageOrder(model.order())
            .build();
    jpaPropertyImageRepository.save(image);
  }

  @Override
  public void verifyByIdAndPropertyId(Long imageId, Long propertyId) {
    jpaPropertyImageRepository
        .findByIdAndPropertyId(imageId, propertyId)
        .orElseThrow(() -> new BusinessException(NOT_FOUND_PROPERTY_IMAGE));
  }

  @Override
  public List<PropertyImageResult> findAllByPropertyId(Long propertyId) {
    List<PropertyImage> images =
        jpaPropertyImageRepository.findAllByPropertyIdOrderByImageOrderAsc(propertyId);
    return images.stream().map(PropertyImageResult::from).toList();
  }

  @Override
  public void delete(Long imageId, Long propertyId) {
    jpaPropertyImageRepository.deleteByIdAndPropertyId(imageId, propertyId);
  }

  @Override
  public void updateOrder(Long propertyId, Integer newOrder) {
    PropertyImage image = em.getReference(PropertyImage.class, propertyId);
    image.updateOrder(newOrder);
    jpaPropertyImageRepository.save(image);
  }
}
