package com.example.dnd_13th_9_be.config.security;

import java.io.IOException;
import java.time.Instant;
import java.util.Map;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import com.example.dnd_13th_9_be.common.utils.CookieUtil;
import com.example.dnd_13th_9_be.common.utils.JWTUtil;
import com.example.dnd_13th_9_be.config.AppProperties;
import com.example.dnd_13th_9_be.config.AppProperties.Security.Jwt;
import com.example.dnd_13th_9_be.user.application.dto.RoleAttribute;
import com.example.dnd_13th_9_be.user.application.model.UserModel;
import com.example.dnd_13th_9_be.user.application.repository.UserRepository;
import com.example.dnd_13th_9_be.user.application.token.RefreshTokenService;

@Component
@RequiredArgsConstructor
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

  private final UserRepository userRepository;
  private final JWTUtil jwtUtil;
  private final AppProperties appProperties;
  private final CookieUtil cookieUtil;
  private final RefreshTokenService refreshTokenService;

  @Override
  public void onAuthenticationSuccess(
      HttpServletRequest request, HttpServletResponse response, Authentication authentication)
      throws IOException {
    OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
    Map<String, Object> attributes = oAuth2User.getAttributes();

    Long userId = (Long) attributes.get("userId");
    UserModel userModel =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다. ID: " + userId));

    RoleAttribute role = userModel.getRole();
    String accessToken = jwtUtil.createAccessToken(userId, role);
    String refreshToken = jwtUtil.createRefreshToken(userId, role);

    Jwt jwt = appProperties.getSecurity().getJwt();
    refreshTokenService.createRefreshToken(
        refreshToken, Instant.now().plusMillis(jwt.getRefreshTokenExpirationMs()), userModel);

    response.addCookie(cookieUtil.create("Access", accessToken, jwt.getAccessTokenExpirationMs()));
    response.addCookie(
        cookieUtil.create("Refresh", refreshToken, jwt.getRefreshTokenExpirationMs()));

    String frontendUrl = appProperties.getSecurity().getOauth2().getFrontendRedirectBaseUrl();
    response.sendRedirect(frontendUrl);
  }
}
