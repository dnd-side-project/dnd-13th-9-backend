package com.example.dnd_13th_9_be.checklist.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.Comment;

@Entity
@Table(name = "checklist_category")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChecklistCategoryEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Comment("메인 공간, 창문, 건물")
  @Column(nullable = false, length = 10)
  private String name;
}
