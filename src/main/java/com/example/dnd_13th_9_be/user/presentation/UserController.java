package com.example.dnd_13th_9_be.user.presentation;

import java.util.Optional;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import com.example.dnd_13th_9_be.common.utils.CookieUtil;
import com.example.dnd_13th_9_be.common.utils.SecurityUtil;
import com.example.dnd_13th_9_be.global.response.ApiResponse;
import com.example.dnd_13th_9_be.user.application.dto.TokenRefreshResponseDto;
import com.example.dnd_13th_9_be.user.application.dto.UserResponseDto;
import com.example.dnd_13th_9_be.user.application.service.UserService;
import com.example.dnd_13th_9_be.user.application.token.AuthService;
import com.example.dnd_13th_9_be.user.application.token.CookieService;
import com.example.dnd_13th_9_be.user.application.token.RefreshTokenService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;
  private final CookieUtil cookieUtil;
  private final RefreshTokenService refreshTokenService;
  private final CookieService cookieService;
  private final AuthService authService;

  @GetMapping("/my")
  public ResponseEntity<ApiResponse<UserResponseDto>> getCurrentUser() {
    UserResponseDto userInfo = userService.getCurrentUser(SecurityUtil.getCurrentUserId());
    return ResponseEntity.ok(ApiResponse.success(userInfo));
  }

  @PostMapping("/logout")
  public ResponseEntity<ApiResponse<String>> logout(
      HttpServletRequest request, HttpServletResponse response) {
    Optional<Cookie> refreshCookie = cookieUtil.find(request.getCookies(), "Refresh");
    refreshCookie.ifPresent(cookie -> refreshTokenService.deleteByToken(cookie.getValue()));
    response.addCookie(cookieUtil.remove("Refresh"));
    response.addCookie(cookieUtil.remove("Access"));
    return ResponseEntity.ok(ApiResponse.success("로그아웃 완료"));
  }

  @PostMapping("/refresh")
  public ResponseEntity<ApiResponse<Void>> refresh(
      HttpServletRequest request, HttpServletResponse response) {
    String refreshToken = cookieService.extractRefreshToken(request);
    TokenRefreshResponseDto tokenResponse = authService.refreshTokens(refreshToken);
    cookieService.setTokenCookies(response, tokenResponse);
    return ResponseEntity.ok(ApiResponse.success(null));
  }
}
