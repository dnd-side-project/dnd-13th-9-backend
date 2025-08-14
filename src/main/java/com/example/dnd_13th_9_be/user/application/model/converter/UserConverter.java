package com.example.dnd_13th_9_be.user.application.model.converter;

import org.springframework.stereotype.Component;

import com.example.dnd_13th_9_be.common.support.converter.AbstractEntityConverter;
import com.example.dnd_13th_9_be.user.application.model.UserModel;
import com.example.dnd_13th_9_be.user.persistence.User;

@Component
public class UserConverter implements AbstractEntityConverter<User, UserModel> {

  @Override
  public UserModel from(User user) {
    return UserModel.builder()
        .id(user.getId())
        .name(user.getName())
        .role(user.getRole())
        .providerId(user.getProviderId())
        .build();
  }

  @Override
  public User toEntity(UserModel userModel) {
    return User.builder()
        .name(userModel.getName())
        .role(userModel.getRole())
        .providerId(userModel.getProviderId())
        .build();
  }
}
