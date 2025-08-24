package com.example.dnd_13th_9_be.property.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.example.dnd_13th_9_be.checklist.persistence.entity.ChecklistItem;
import com.example.dnd_13th_9_be.common.persistence.BaseEntity;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@Table(
    name = "property_required_check",
    uniqueConstraints =
    @UniqueConstraint(
        name = "uk_property_required_check_property_item",
        columnNames = {"property_id", "item_id"}))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PropertyRequiredCheck extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "property_id", nullable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Property property;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "item_id", nullable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  private ChecklistItem checklistItem;

  @Builder
  public PropertyRequiredCheck(Property property, ChecklistItem checklistItem) {
    this.property = property;
    this.checklistItem = checklistItem;
  }
}
