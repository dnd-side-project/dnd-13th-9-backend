package com.example.dnd_13th_9_be.checklist.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.example.dnd_13th_9_be.common.persistence.BaseEntity;
import com.example.dnd_13th_9_be.user.persistence.User;

@Entity
@Table(
    name = "user_required_item",
    uniqueConstraints = {
      @UniqueConstraint(
          name = "uk_user_required_item_user_item",
          columnNames = {"user_id", "item_id"})
    },
    indexes = {
      @Index(name = "idx_user_required_item_user", columnList = "user_id"),
      @Index(name = "idx_user_required_item_item", columnList = "item_id")
    })
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserRequiredItem extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(
      name = "user_id",
      nullable = false,
      foreignKey = @ForeignKey(name = "fk_user_required_item_user"))
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(
      name = "item_id",
      nullable = false,
      foreignKey = @ForeignKey(name = "fk_user_required_item_item"))
  private ChecklistItem item;

  @Builder
  public UserRequiredItem(Long id, User user, ChecklistItem item) {
    this.id = id;
    this.user = user;
    this.item = item;
  }
}
