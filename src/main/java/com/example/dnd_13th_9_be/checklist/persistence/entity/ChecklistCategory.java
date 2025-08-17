package com.example.dnd_13th_9_be.checklist.persistence.entity;

import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.example.dnd_13th_9_be.common.persistence.BaseEntity;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "checklist_category")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChecklistCategory extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Comment("메인 공간, 창문, 건물")
  @Column(nullable = false, length = 10)
  private String name;

  @Comment("노출 순서")
  @Column(name = "sort_order", nullable = false)
  private Integer sortOrder;

  @OrderBy("sortOrder ASC")
  @OneToMany(
      mappedBy = "category",
      cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  private List<ChecklistItem> items;

  @Builder
  ChecklistCategory(String name, Integer sortOrder) {
    this.name = name;
    this.sortOrder = sortOrder;
  }
}
