package com.example.dnd_13th_9_be.checklist.persistence;

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

import com.example.dnd_13th_9_be.property.persistence.PropertyRecordEntity;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "property_checklist_answer")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PropertyChecklistAnswerEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "property_id", nullable = false)
  private PropertyRecordEntity propertyRecord;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "item_id", nullable = false)
  private ChecklistItemEntity item;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "option_id", nullable = false)
  private ChecklistOptionEntity option;

  @Comment("필수 확인 여부")
  @Column(name = "required_confirm_yn")
  @ColumnDefault("false")
  private boolean requiredConfirmYN;
}
