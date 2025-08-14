package com.example.dnd_13th_9_be.user.persistence;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaRefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

  Optional<RefreshToken> findByToken(String token);

  @Query("SELECT rt FROM RefreshToken rt WHERE rt.user.id = :userId")
  Optional<RefreshToken> findByUserId(@Param("userId") Long userId);

  boolean existsByToken(String token);

  @Query(
      "SELECT CASE WHEN COUNT(rt) > 0 THEN true ELSE false END FROM RefreshToken rt WHERE rt.user.id = :userId")
  boolean existsByUserId(@Param("userId") Long userId);

  void deleteByToken(String token);

  @Modifying
  @Query("DELETE FROM RefreshToken rt WHERE rt.user.id = :userId")
  void deleteByUserId(@Param("userId") Long userId);

  @Modifying
  @Query("DELETE FROM RefreshToken rt WHERE rt.expiryDate < :now")
  void deleteByExpiryDateBefore(@Param("now") Instant now);

  @Query("SELECT rt FROM RefreshToken rt WHERE rt.expiryDate < :now")
  List<RefreshToken> findExpiredTokens(@Param("now") Instant now);

  @Modifying
  @Query("DELETE FROM RefreshToken rt WHERE rt.expiryDate < :cutoffDate")
  void deleteOldTokens(@Param("cutoffDate") Instant cutoffDate);
}
