package com.example.dnd_13th_9_be.property.persistence;

import java.util.List;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.dnd_13th_9_be.property.persistence.entity.PropertyImage;

@Repository
public interface JpaPropertyImageRepository extends JpaRepository<PropertyImage, Long> {
  Optional<PropertyImage> findByIdAndPropertyId(Long imageId, Long propertyId);
  List<PropertyImage> findAllByPropertyIdOrderByImageOrderAsc(Long propertyId);
  void deleteByIdAndPropertyId(Long imageId, Long propertyId);
}
