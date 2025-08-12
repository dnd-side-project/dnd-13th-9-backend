package com.example.dnd_13th_9_be.property.persistence;

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

import org.hibernate.annotations.Comment;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "property_transport")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PropertyTransportEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "property_id", nullable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  private PropertyRecordEntity propertyRecord;

  @Comment("목적지")
  @Column(name = "destination_name", nullable = false)
  private String destinationName;

  @Comment("이동시간")
  @Column(name = "transit_time", nullable = false)
  private int transitTime;
}
