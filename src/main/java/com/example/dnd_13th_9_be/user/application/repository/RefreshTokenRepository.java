package com.example.dnd_13th_9_be.user.application.repository;

import java.time.Instant;
import java.util.Optional;

import com.example.dnd_13th_9_be.global.error.InvalidTokenException;
import com.example.dnd_13th_9_be.user.application.model.RefreshTokenModel;

public interface RefreshTokenRepository {

  RefreshTokenModel save(RefreshTokenModel refreshTokenModel);

  /**
   * @throws InvalidTokenException
   */
  RefreshTokenModel findByToken(String token);

  Optional<RefreshTokenModel> findByUserId(Long userId);

  void deleteByToken(String token);

  void deleteByUserId(Long userId);

  void deleteExpiredTokens(Instant now);

  boolean existsByToken(String token);
}
