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

import com.example.dnd_13th_9_be.checklist.persistence.entity.ChecklistItem;
import com.example.dnd_13th_9_be.common.persistence.BaseEntity;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@Table(name = "property_required_check")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PropertyRequiredCheck extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "checklist_id", insertable = false, updatable = false)
  private Long checklistItemId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "checklist_id", nullable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  private ChecklistItem checklistItem;

  @Builder
  public PropertyRequiredCheck(ChecklistItem checklistItem) {
    this.checklistItem = checklistItem;
  }
}
