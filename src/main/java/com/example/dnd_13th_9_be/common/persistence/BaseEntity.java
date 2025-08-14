package com.example.dnd_13th_9_be.common.persistence;

import java.sql.Timestamp;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})
public class BaseEntity {

  @Column(nullable = false, updatable = false)
  @CreationTimestamp
  private Timestamp createdAt;

  @Column(nullable = false)
  @UpdateTimestamp
  private Timestamp updatedAt;
}
