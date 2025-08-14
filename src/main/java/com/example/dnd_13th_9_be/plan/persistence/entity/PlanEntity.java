package com.example.dnd_13th_9_be.plan.persistence.entity;

import java.time.LocalDateTime;
import java.util.Set;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.example.dnd_13th_9_be.folder.persistence.FolderEntity;
import com.example.dnd_13th_9_be.user.persistence.User;
import com.example.dnd_13th_9_be.global.converter.BooleanAttributeConverter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "plan")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlanEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Comment("계획 이름")
  @Column(nullable = false, length = 10)
  private String name;

  @Comment("기본 계획 여부")
  @Column(name = "default_yn", nullable = false, columnDefinition = "TINYINT", length = 1)
  @Convert(converter = BooleanAttributeConverter.class)
  @ColumnDefault("0")
  private Boolean isDefault;

  @CreationTimestamp
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @OneToMany(
      mappedBy = "plan",
      cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  private Set<FolderEntity> folders;

  private PlanEntity(User user, String name, boolean isDefault) {
    this.user = user;
    this.name = name;
    this.isDefault = isDefault;
  }

  public static PlanEntity of(User user, String name, boolean isDefault) {
    return new PlanEntity(user, name, isDefault);
  }
}
