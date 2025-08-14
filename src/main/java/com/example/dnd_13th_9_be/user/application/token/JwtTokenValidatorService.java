package com.example.dnd_13th_9_be.user.application.token;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.example.dnd_13th_9_be.common.utils.JWTUtil;
import com.example.dnd_13th_9_be.global.error.InvalidTokenException;
import com.example.dnd_13th_9_be.user.application.dto.RoleAttribute;
import com.example.dnd_13th_9_be.user.application.dto.UserPrincipleDto;
import com.example.dnd_13th_9_be.user.application.model.UserModel;
import com.example.dnd_13th_9_be.user.application.repository.UserRepository;
import com.google.common.base.Strings;

import static com.example.dnd_13th_9_be.global.error.ErrorCode.INVALID_TOKEN;
import static com.example.dnd_13th_9_be.global.error.ErrorCode.TOKEN_EXPIRED;

@Service
@RequiredArgsConstructor
public class JwtTokenValidatorService {

  private final JWTUtil jwtUtil;
  private final UserRepository userRepository;

  public UserPrincipleDto validateToken(String token) {
    if (Strings.isNullOrEmpty(token)) {
      throw new InvalidTokenException();
    }

    if (jwtUtil.isExpired(token)) {
      throw new InvalidTokenException(TOKEN_EXPIRED);
    }

    Long userId = jwtUtil.getUserId(token);
    if (userId == null) {
      throw new InvalidTokenException(INVALID_TOKEN, "userId Claim이 없습니다.");
    }

    RoleAttribute role = jwtUtil.getRole(token);
    if (role == null) {
      throw new InvalidTokenException(INVALID_TOKEN, "Role claim이 없습니다.");
    }

    UserModel userModel =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다. ID: " + userId));
    return UserPrincipleDto.from(userModel);
  }
}
