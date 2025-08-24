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
import jakarta.persistence.UniqueConstraint;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.example.dnd_13th_9_be.checklist.persistence.entity.ChecklistCategory;
import com.example.dnd_13th_9_be.common.persistence.BaseEntity;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(
    name = "property_category_memo",
    uniqueConstraints =
        @UniqueConstraint(
            name = "uk_pcm_property_category",
            columnNames = {"property_id", "category_id"}))
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PropertyCategoryMemo extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id", nullable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  private ChecklistCategory category;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "property_id", nullable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Property property;

  @Comment("카테고리별 메모")
  @Column(name = "memo", length = 200)
  private String memo;

  @Builder
  public PropertyCategoryMemo(ChecklistCategory category, Property property, String memo) {
    this.property = property;
    this.category = category;
    this.memo = memo;
  }

  public void updateMemo(String memo) {
    this.memo = memo;
  }
}
