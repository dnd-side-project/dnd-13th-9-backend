package com.example.dnd_13th_9_be.user.application.token;

import java.time.Instant;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.example.dnd_13th_9_be.global.error.InvalidTokenException;
import com.example.dnd_13th_9_be.user.application.model.RefreshTokenModel;
import com.example.dnd_13th_9_be.user.application.model.UserModel;
import com.example.dnd_13th_9_be.user.application.repository.RefreshTokenRepository;
import com.example.dnd_13th_9_be.user.persistence.JpaRefreshTokenRepository;
import com.example.dnd_13th_9_be.user.persistence.JpaUserRepository;
import com.example.dnd_13th_9_be.user.persistence.RefreshToken;
import com.example.dnd_13th_9_be.user.persistence.User;

import static com.example.dnd_13th_9_be.user.persistence.QUser.user;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

  private final RefreshTokenRepository refreshTokenRepository;
  private final JpaRefreshTokenRepository jpaRefreshTokenRepository;
  private final JpaUserRepository jpaUserRepository;

  // TODO: 모델
  @Transactional
  public void createRefreshToken(String token, Instant expiration, UserModel userModel) {
    Long userId = userModel.getId();

    try {
      jpaRefreshTokenRepository.deleteByUserId(userId);
      jpaRefreshTokenRepository.flush();

      User userEntity = jpaUserRepository.findById(userId)
              .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다"));

      RefreshToken refreshTokenEntity = RefreshToken.builder()
              .token(token)
              .expiryDate(expiration)
              .user(userEntity)
              .build();

      jpaRefreshTokenRepository.save(refreshTokenEntity);
    } catch (Exception e) {
      throw new RuntimeException("리프레시 토큰 생성에 실패했습니다", e);
    }
  }

  @Transactional
  public RefreshTokenModel getRefreshToken(String token) {
    RefreshTokenModel refreshToken = refreshTokenRepository.findByToken(token);
    if (refreshToken.isInValid()) {
      throw new InvalidTokenException("유효하지 않은 리프레시 토큰입니다.");
    }
    return refreshToken;
  }

  @Transactional
  public UserModel findUserByRefreshToken(String token) {
    RefreshTokenModel refreshTokenModel = refreshTokenRepository.findByToken(token);

    if (refreshTokenModel.isInValid()) {
      throw new InvalidTokenException("만료된 리프레시 토큰입니다.");
    }

    return refreshTokenModel.getUser();
  }

  @Transactional
  public void deleteByToken(String token) {
    refreshTokenRepository.deleteByToken(token);
  }

  public void revokeAllByUserId(Long userId) {
    refreshTokenRepository.deleteByUserId(userId);
  }
}
