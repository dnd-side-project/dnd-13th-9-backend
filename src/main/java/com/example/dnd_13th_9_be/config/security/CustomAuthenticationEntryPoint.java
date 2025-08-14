package com.example.dnd_13th_9_be.config.security;

import java.io.IOException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import com.example.dnd_13th_9_be.global.response.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import static com.example.dnd_13th_9_be.global.error.ErrorCode.TOKEN_UNAUTHORIZED;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

  private final ObjectMapper objectMapper;

  @Override
  public void commence(
      HttpServletRequest request,
      HttpServletResponse response,
      AuthenticationException authException)
      throws IOException {

    String xhrHeader = request.getHeader("X-Requested-With");
    String accept = request.getHeader("Accept");

    boolean isAjax =
        "XMLHttpRequest".equalsIgnoreCase(xhrHeader)
            || (accept != null && accept.contains("application/json"));

    if (isAjax) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.setContentType("application/json");

      String apiResponse = objectMapper.writeValueAsString(ApiResponse.error(TOKEN_UNAUTHORIZED));
      response.getWriter().write(apiResponse);
    }
  }
}
