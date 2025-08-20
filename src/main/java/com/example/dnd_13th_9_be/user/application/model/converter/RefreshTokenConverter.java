package com.example.dnd_13th_9_be.user.application.model.converter;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import com.example.dnd_13th_9_be.common.support.converter.AbstractEntityConverter;
import com.example.dnd_13th_9_be.user.application.model.RefreshTokenModel;
import com.example.dnd_13th_9_be.user.persistence.RefreshToken;

@Component
@RequiredArgsConstructor
public class RefreshTokenConverter
    implements AbstractEntityConverter<RefreshToken, RefreshTokenModel> {

  private final UserConverter userConverter;

  @Override
  public RefreshTokenModel from(RefreshToken refreshToken) {
    return RefreshTokenModel.builder()
        .id(refreshToken.getId())
        .token(refreshToken.getToken())
        .expiryDate(refreshToken.getExpiryDate())
        .user(userConverter.from(refreshToken.getUser()))
        .build();
  }

  @Override
  public RefreshToken toEntity(RefreshTokenModel refreshTokenModel) {
    return RefreshToken.builder()
        .token(refreshTokenModel.getToken())
        .expiryDate(refreshTokenModel.getExpiryDate())
        .user(userConverter.toEntity(refreshTokenModel.getUser()))
        .build();
  }
}
