package com.example.dnd_13th_9_be.user.persistence;

import jakarta.persistence.*;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.example.dnd_13th_9_be.common.persistence.BaseEntity;
import com.example.dnd_13th_9_be.user.application.dto.RoleAttribute;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(name = "oauth_provider_id", nullable = false, unique = true)
  private String providerId;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private RoleAttribute role;

  @Builder
  public User(String name, String providerId, RoleAttribute role) {
    this.name = name;
    this.providerId = providerId;
    this.role = role;
  }
}
