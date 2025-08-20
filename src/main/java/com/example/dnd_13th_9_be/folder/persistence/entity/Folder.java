package com.example.dnd_13th_9_be.folder.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
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

import com.example.dnd_13th_9_be.common.persistence.BaseEntity;
import com.example.dnd_13th_9_be.global.converter.BooleanAttributeConverter;
import com.example.dnd_13th_9_be.plan.persistence.entity.Plan;
import com.example.dnd_13th_9_be.user.persistence.User;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "folder")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Folder extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "plan_id", nullable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Plan plan;

  @Comment("폴더 명")
  @Column(nullable = false, length = 10)
  private String name;

  @Comment("기본 폴더 여부")
  @Column(name = "default_yn", nullable = false, columnDefinition = "TINYINT", length = 1)
  @Convert(converter = BooleanAttributeConverter.class)
  @ColumnDefault("0")
  private Boolean isDefault;

  private Folder(User user, Plan plan, String name, boolean isDefault) {
    this.user = user;
    this.plan = plan;
    this.name = name;
    this.isDefault = isDefault;
  }

  public static Folder of(User user, Plan plan, String name, boolean isDefault) {
    return new Folder(user, plan, name, isDefault);
  }
}
