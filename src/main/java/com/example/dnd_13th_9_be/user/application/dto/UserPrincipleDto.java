package com.example.dnd_13th_9_be.user.application.dto;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Builder;
import lombok.Getter;

import com.example.dnd_13th_9_be.user.application.model.UserModel;

@Getter
@Builder
public class UserPrincipleDto implements UserDetails {

  private Long userId;
  private RoleAttribute role;

  public static UserPrincipleDto from(UserModel user) {
    return UserPrincipleDto.builder().userId(user.getId()).role(user.getRole()).build();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(role);
  }

  @Override
  public String getPassword() {
    return null;
  }

  @Override
  public String getUsername() {
    return userId.toString();
  }
}
