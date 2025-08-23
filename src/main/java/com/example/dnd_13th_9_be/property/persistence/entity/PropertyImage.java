package com.example.dnd_13th_9_be.property.persistence.entity;

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

import com.example.dnd_13th_9_be.common.persistence.BaseEntity;

@Entity
@Table(name = "property_image")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PropertyImage extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "property_id", nullable = false)
  private Property property;

  @Column(name = "image_url", nullable = false)
  private String imageUrl;

  @Column(name = "image_order", nullable = false)
  private Integer imageOrder;

  @Builder
  public PropertyImage(Property property, String imageUrl, Integer imageOrder) {
    this.property = property;
    this.imageUrl = imageUrl;
    this.imageOrder = imageOrder;
  }

  void setProperty(Property property) {
    this.property = property;
  }

  public void updateOrder(Integer newOrder) {
    this.imageOrder = newOrder;
  }
}
