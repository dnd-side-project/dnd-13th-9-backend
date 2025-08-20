package com.example.dnd_13th_9_be.user.application.dto;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleAttribute implements GrantedAuthority {
  ROLE_USER("정회원"),
  ROLE_GUEST("게스트");

  private final String desc;

  @Override
  public String getAuthority() {
    return this.name();
  }
}
