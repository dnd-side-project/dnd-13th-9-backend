package com.example.dnd_13th_9_be.property.persistence.entity;

import com.example.dnd_13th_9_be.common.persistence.BaseEntity;
import com.example.dnd_13th_9_be.property.application.model.PropertyImageModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "property_image")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PropertyImage extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "image_url", nullable = false)
  private String imageUrl;

  @Column(name = "image_order", nullable = false)
  private Integer imageOrder;

  @Builder
  public PropertyImage(String imageUrl, Integer imageOrder) {
    this.imageUrl = imageUrl;
    this.imageOrder = imageOrder;
  }
}
