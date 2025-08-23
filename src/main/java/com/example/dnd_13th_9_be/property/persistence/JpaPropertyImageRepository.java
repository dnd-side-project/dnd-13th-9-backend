package com.example.dnd_13th_9_be.property.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.dnd_13th_9_be.property.persistence.entity.PropertyImage;

@Repository
public interface JpaPropertyImageRepository extends JpaRepository<PropertyImage, Long> {
  List<PropertyImage> findAllByPropertyId(Long propertyId);
}
