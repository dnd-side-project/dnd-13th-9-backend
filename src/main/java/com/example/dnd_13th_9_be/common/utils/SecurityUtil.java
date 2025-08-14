package com.example.dnd_13th_9_be.common.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.dnd_13th_9_be.user.application.dto.UserPrincipleDto;

public class SecurityUtil {

  public static Long getCurrentUserId() {
    return getCurrentUserPrincipal().getUserId();
  }

  public static UserPrincipleDto getCurrentUserPrincipal() {
    return extractUserPrincipal();
  }

  private static UserPrincipleDto extractUserPrincipal() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !authentication.isAuthenticated()) {
      throw new IllegalArgumentException("현재 인증 정보를 찾을 수 없습니다.");
    }
    Object principal = authentication.getPrincipal();
    if (principal instanceof UserPrincipleDto userPrincipleDto) {
      return userPrincipleDto;
    }
    throw new IllegalArgumentException("인증된 사용자 정보를 찾을 수 없습니다.");
  }
}
