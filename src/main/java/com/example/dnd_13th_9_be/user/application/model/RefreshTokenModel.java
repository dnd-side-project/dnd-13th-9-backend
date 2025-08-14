package com.example.dnd_13th_9_be.user.application.model;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.example.dnd_13th_9_be.common.support.AbstractModel;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class RefreshTokenModel implements AbstractModel {
  private Long id;
  private String token;
  private Instant expiryDate;
  private UserModel user;

  public boolean isExpired() {
    return Instant.now().isAfter(this.expiryDate);
  }

  public boolean isTokenMatched(String currentToken) {
    return this.token != null && this.token.equals(currentToken);
  }

  public RefreshTokenModel updateToken(String newToken, Instant newExpiryDate) {
    return this.toBuilder().token(newToken).expiryDate(newExpiryDate).build();
  }

  public boolean isValid() {
    return this.token != null && !isExpired();
  }
}
