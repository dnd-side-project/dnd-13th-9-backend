package com.example.dnd_13th_9_be.checklist.persistence.entity;

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

@Entity
@Table(name = "checklist_item")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChecklistItemEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id", nullable = false)
  private ChecklistCategoryEntity category;

  @Comment("질문")
  @Column(nullable = false, length = 100)
  private String question;

  @Comment("질문 설명")
  @Column(length = 100)
  private String description;

  @Comment("노출 순서")
  @Column(name = "sort_order", nullable = false)
  private Integer sortOrder;
}
