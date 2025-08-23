package com.example.dnd_13th_9_be.property.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.dnd_13th_9_be.property.persistence.entity.Property;

@Repository
public interface JpaPropertyRepository extends JpaRepository<Property, Long> {
  @EntityGraph(value = "Property.detail", type = EntityGraph.EntityGraphType.LOAD)
  @Query("select p from Property p where p.id = :id")
  Optional<Property> findDetailById(@Param("id") Long id);
}
