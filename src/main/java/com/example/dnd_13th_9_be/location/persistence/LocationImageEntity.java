package com.example.dnd_13th_9_be.location.persistence;

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
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "location_image")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LocationImageEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "location_id", nullable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  private LocationRecordEntity locationRecord;

  @Column(name = "image_url", nullable = false)
  private String imageUrl;

  @Column(name = "image_order", nullable = false)
  private Integer imageOrder;
}
