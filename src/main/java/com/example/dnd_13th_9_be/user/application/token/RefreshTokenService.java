package com.example.dnd_13th_9_be.user.application.token;

import java.time.Instant;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.example.dnd_13th_9_be.global.error.InvalidTokenException;
import com.example.dnd_13th_9_be.global.error.UserNotFoundException;
import com.example.dnd_13th_9_be.user.application.model.RefreshTokenModel;
import com.example.dnd_13th_9_be.user.application.model.UserModel;
import com.example.dnd_13th_9_be.user.application.repository.RefreshTokenRepository;
import com.example.dnd_13th_9_be.user.persistence.JpaRefreshTokenRepository;
import com.example.dnd_13th_9_be.user.persistence.JpaUserRepository;
import com.example.dnd_13th_9_be.user.persistence.RefreshToken;
import com.example.dnd_13th_9_be.user.persistence.User;

import static com.example.dnd_13th_9_be.global.error.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

  private final RefreshTokenRepository refreshTokenRepository;
  private final JpaRefreshTokenRepository jpaRefreshTokenRepository;
  private final JpaUserRepository jpaUserRepository;

  // TODO: 영속화 리팩토링
  @Transactional
  public void createRefreshToken(String token, Instant expiration, UserModel userModel) {
    Long userId = userModel.getId();

    try {
      jpaRefreshTokenRepository.deleteByUserId(userId);
      jpaRefreshTokenRepository.flush();

      User userEntity =
          jpaUserRepository
              .findById(userId)
              .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));

      RefreshToken refreshTokenEntity =
          RefreshToken.builder().token(token).expiryDate(expiration).user(userEntity).build();

      jpaRefreshTokenRepository.save(refreshTokenEntity);
    } catch (Exception e) {
      throw new InvalidTokenException(REFRESH_TOKEN_NOT_CREATED);
    }
  }

  @Transactional
  public RefreshTokenModel getRefreshToken(String token) {
    return refreshTokenRepository
        .findByToken(token)
        .filter(RefreshTokenModel::isValid)
        .orElseThrow(() -> new InvalidTokenException(INVALID_REFRESH_TOKEN));
  }

  @Transactional
  public UserModel findUserByRefreshToken(String token) {
    RefreshTokenModel refreshTokenModel = getRefreshToken(token);
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
