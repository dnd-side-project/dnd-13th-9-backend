package com.example.dnd_13th_9_be.common.utils;

import com.example.dnd_13th_9_be.global.error.UserNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.dnd_13th_9_be.user.application.dto.UserPrincipalDto;

public class SecurityUtil {

  public static Long getCurrentUserId() {
    return getCurrentUserPrincipal().getUserId();
  }

  public static UserPrincipalDto getCurrentUserPrincipal() {
    return extractUserPrincipal();
  }

  private static UserPrincipalDto extractUserPrincipal() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !authentication.isAuthenticated()) {
      throw new UserNotFoundException("현재 인증 정보를 찾을 수 없습니다.");
    }
    Object principal = authentication.getPrincipal();
    if (principal instanceof UserPrincipalDto userPrincipalDto) {
      return userPrincipalDto;
    }
    throw new UserNotFoundException("인증된 사용자 정보를 찾을 수 없습니다.");
  }
}
