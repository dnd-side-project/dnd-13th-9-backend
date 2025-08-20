package com.example.dnd_13th_9_be.user.persistence;

import java.time.Instant;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

import com.example.dnd_13th_9_be.user.application.model.RefreshTokenModel;
import com.example.dnd_13th_9_be.user.application.model.converter.RefreshTokenConverter;
import com.example.dnd_13th_9_be.user.application.repository.RefreshTokenRepository;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository {

  private final JpaRefreshTokenRepository jpaRefreshTokenRepository;
  private final RefreshTokenConverter refreshTokenConverter;

  @Override
  public RefreshTokenModel save(RefreshTokenModel refreshTokenModel) {
    RefreshToken refreshToken = refreshTokenConverter.toEntity(refreshTokenModel);
    RefreshToken savedRefreshToken = jpaRefreshTokenRepository.save(refreshToken);
    return refreshTokenConverter.from(savedRefreshToken);
  }

  @Override
  public Optional<RefreshTokenModel> findByToken(String token) {
    return jpaRefreshTokenRepository.findByToken(token).map(refreshTokenConverter::from);
  }

  @Override
  public Optional<RefreshTokenModel> findByUserId(Long userId) {
    return jpaRefreshTokenRepository.findByUserId(userId).map(refreshTokenConverter::from);
  }

  @Override
  public void deleteByToken(String token) {
    jpaRefreshTokenRepository.deleteByToken(token);
  }

  @Override
  public void deleteByUserId(Long userId) {
    jpaRefreshTokenRepository.deleteByUserId(userId);
  }

  @Override
  public void deleteExpiredTokens(Instant now) {
    jpaRefreshTokenRepository.deleteByExpiryDateBefore(now);
  }

  @Override
  public boolean existsByToken(String token) {
    return jpaRefreshTokenRepository.existsByToken(token);
  }
}
