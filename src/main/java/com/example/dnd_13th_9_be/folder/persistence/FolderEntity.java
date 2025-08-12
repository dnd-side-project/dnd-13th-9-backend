package com.example.dnd_13th_9_be.folder.persistence;

import java.time.LocalDateTime;
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

import com.example.dnd_13th_9_be.plan.persistence.PlanEntity;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "folder")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FolderEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "plan_id", nullable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  private PlanEntity plan;

  @Comment("폴더 명")
  @Column(nullable = false)
  private String name;

  @Comment("기본 폴더 여부")
  @Column(name = "default_yn", nullable = false)
  private boolean isDefault;

  @CreationTimestamp
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  private FolderEntity(PlanEntity plan, String name, boolean isDefault) {
    this.plan = plan;
    this.name = name;
    this.isDefault = isDefault;
  }

  public static FolderEntity of(PlanEntity plan, String name, boolean isDefault) {
    return new FolderEntity(plan, name, isDefault);
  }
}
