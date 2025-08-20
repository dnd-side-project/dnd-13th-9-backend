package com.example.dnd_13th_9_be.config.security;

import java.io.IOException;
import java.util.Optional;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.example.dnd_13th_9_be.common.utils.CookieUtil;
import com.example.dnd_13th_9_be.global.error.JwtAuthenticationException;
import com.example.dnd_13th_9_be.user.application.dto.UserPrincipalDto;
import com.example.dnd_13th_9_be.user.application.token.JwtTokenValidatorService;

@Slf4j
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

  private final AuthenticationEntryPoint entryPoint;
  private final JwtTokenValidatorService jwtTokenValidatorService;
  private final CookieUtil cookieUtil;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    if (request.getRequestURI().startsWith("/api-docs")
        || request.getRequestURI().startsWith("/swagger-ui")
        || request.getRequestURI().startsWith("/login/oauth2/")
        || request.getRequestURI().startsWith("/oauth2/authorization")
        || request.getRequestURI().equals("/api/auth/refresh")
        || request.getRequestURI().equals("/api/test/auth/token")) {
      filterChain.doFilter(request, response);
      return;
    }

    try {

      Optional<Cookie> OptionalCookie = cookieUtil.find(request.getCookies(), "Access");
      Cookie cookie =
          OptionalCookie.orElseThrow(() -> new IllegalStateException("Access Cookie not found"));
      String accessToken = cookie.getValue();
      UserPrincipalDto userPrincipalDto = jwtTokenValidatorService.validateToken(accessToken);
      Authentication authentication =
          new UsernamePasswordAuthenticationToken(
              userPrincipalDto, null, userPrincipalDto.getAuthorities());

      SecurityContextHolder.getContext().setAuthentication(authentication);
      filterChain.doFilter(request, response);
    } catch (Exception e) {
      log.info("JWT 필터에서 Access 토큰 검증 도중 예외 발생 -> {}", e.getMessage());

      entryPoint.commence(request, response, new JwtAuthenticationException(e.getMessage(), e) {});
    }
  }
}
