package com.example.dnd_13th_9_be.property.persistence;

import com.example.dnd_13th_9_be.property.application.dto.PropertyImageDto;
import com.example.dnd_13th_9_be.property.application.model.PropertyImageModel;
import com.example.dnd_13th_9_be.property.application.model.converter.PropertyImageConverter;
import com.example.dnd_13th_9_be.property.application.repository.PropertyImageRepository;
import com.example.dnd_13th_9_be.property.persistence.entity.Property;
import com.example.dnd_13th_9_be.property.persistence.entity.PropertyImage;
import jakarta.persistence.EntityManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class PropertyImageRepositoryImpl implements PropertyImageRepository {
    private final EntityManager em;
    private final JpaPropertyImageRepository jpaPropertyImageRepository;
    private final PropertyImageConverter propertyImageConverter;

    @Override
    public void save(PropertyImageDto model) {
        Property property = em.getReference(Property.class, model.propertyId());
        PropertyImage image = PropertyImage.builder()
            .property(property)
            .imageUrl(model.imageUrl())
            .imageOrder(model.order())
            .build();
        PropertyImage saved = jpaPropertyImageRepository.save(image);
        log.info("tlq: {}", saved);
    }

    @Override
    public List<PropertyImageModel> findAllByPropertyId(Long propertyId) {
        List<PropertyImage> images = jpaPropertyImageRepository.findAllByPropertyId(propertyId);
        return images.stream().map(propertyImageConverter::from).toList();
    }

    @Override
    public void delete(Long imageId) {
        jpaPropertyImageRepository.deleteById(imageId);
    }

    @Override
    public void updateOrder(Long propertyId, Integer newOrder) {
        PropertyImage image = em.getReference(PropertyImage.class, propertyId);
        image.updateOrder(newOrder);
        jpaPropertyImageRepository.save(image);
    }
}
