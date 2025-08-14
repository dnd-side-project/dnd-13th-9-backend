package com.example.dnd_13th_9_be.user.application.token;

import java.time.Instant;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.example.dnd_13th_9_be.common.utils.JWTUtil;
import com.example.dnd_13th_9_be.config.AppProperties;
import com.example.dnd_13th_9_be.config.AppProperties.Security.Jwt;
import com.example.dnd_13th_9_be.user.application.dto.TokenRefreshResponseDto;
import com.example.dnd_13th_9_be.user.application.model.RefreshTokenModel;
import com.example.dnd_13th_9_be.user.application.model.UserModel;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final RefreshTokenService refreshTokenService;
  private final JWTUtil jwtUtil;
  private final AppProperties appProperties;

  @Transactional
  public TokenRefreshResponseDto refreshTokens(String refreshToken) {
    RefreshTokenModel refreshTokenModel = refreshTokenService.getRefreshToken(refreshToken);
    UserModel user = refreshTokenModel.getUser();

    String newAccessToken = jwtUtil.createAccessToken(user.getId(), user.getRole());
    String newRefreshToken = jwtUtil.createRefreshToken(user.getId(), user.getRole());

    Jwt jwt = appProperties.getSecurity().getJwt();
    refreshTokenService.createRefreshToken(
        newRefreshToken, Instant.now().plusMillis(jwt.getRefreshTokenExpirationMs()), user);

    return TokenRefreshResponseDto.builder()
        .accessToken(newAccessToken)
        .refreshToken(newRefreshToken)
        .accessTokenExpiresIn(jwt.getAccessTokenExpirationMs())
        .refreshTokenExpiresIn(jwt.getRefreshTokenExpirationMs())
        .build();
  }
}
