package com.example.dnd_13th_9_be.property.persistence;

import com.example.dnd_13th_9_be.property.persistence.entity.PropertyRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaPropertyRecordRepository extends JpaRepository<PropertyRecord, Long> {

}
