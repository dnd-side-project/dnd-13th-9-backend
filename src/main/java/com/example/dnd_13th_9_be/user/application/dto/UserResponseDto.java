package com.example.dnd_13th_9_be.user.application.dto;

import lombok.Builder;
import lombok.Getter;

import com.example.dnd_13th_9_be.user.application.model.UserModel;

@Getter
@Builder
public class UserResponseDto {
  private String name;
  private String providerId;
  private RoleAttribute role;

  public static UserResponseDto from(UserModel userModel) {
    return UserResponseDto.builder()
        .name(userModel.getName())
        .role(userModel.getRole())
        .providerId(userModel.getProviderId())
        .build();
  }
}
