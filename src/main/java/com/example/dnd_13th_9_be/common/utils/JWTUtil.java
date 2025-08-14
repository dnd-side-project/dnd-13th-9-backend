package com.example.dnd_13th_9_be.common.utils;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

import com.example.dnd_13th_9_be.config.AppProperties;
import com.example.dnd_13th_9_be.user.application.dto.RoleAttribute;
import com.google.common.base.Strings;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class JWTUtil {

  private final SecretKey secretKey;
  private final AppProperties appProperties;

  public JWTUtil(AppProperties appProperties) {
    this.appProperties = appProperties;

    String secret = appProperties.getSecurity().getJwt().getSecret();
    this.secretKey =
        new SecretKeySpec(
            secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
  }

  public String createAccessToken(Long userId, RoleAttribute role, Long expiredMs) {
    return Jwts.builder()
        .id(UUID.randomUUID().toString())
        .subject(String.valueOf(userId))
        .claim("role", role.name())
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + expiredMs))
        .signWith(secretKey)
        .compact();
  }

  public String createAccessToken(Long userId, RoleAttribute role) {
    return this.createAccessToken(
        userId, role, appProperties.getSecurity().getJwt().getAccessTokenExpirationMs());
  }

  public String createRefreshToken(Long userId, RoleAttribute role, Long expiredMs) {
    return Jwts.builder()
        .id(UUID.randomUUID().toString())
        .subject(String.valueOf(userId))
        .claim("role", role.name())
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + expiredMs))
        .signWith(secretKey)
        .compact();
  }

  public String createRefreshToken(Long userId, RoleAttribute role) {
    return this.createRefreshToken(
        userId, role, appProperties.getSecurity().getJwt().getRefreshTokenExpirationMs());
  }

  public Long getUserId(String token) {
    return Long.parseLong(getPayload(token).getSubject());
  }

  public RoleAttribute getRole(String token) {
    String roleString = getPayload(token).get("role", String.class);
    if (Strings.isNullOrEmpty(roleString)) return null;
    return RoleAttribute.valueOf(roleString);
  }

  public boolean isExpired(String token) {
    try {
      return getPayload(token).getExpiration().before(new Date());
    } catch (Exception e) {
      return true;
    }
  }

  private Claims getPayload(String token) {
    return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
  }
}
