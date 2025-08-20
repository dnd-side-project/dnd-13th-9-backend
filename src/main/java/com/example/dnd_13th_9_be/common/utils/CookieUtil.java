package com.example.dnd_13th_9_be.common.utils;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import jakarta.servlet.http.Cookie;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CookieUtil {

  public Cookie create(String key, String value, long expirationMs) {
    Cookie cookie = new Cookie(key, value);
    cookie.setMaxAge((int) (expirationMs / 1000)); // 초 단위로 변환
    cookie.setPath("/");
    cookie.setHttpOnly(true);
    cookie.setSecure(true); // TODO: https 적용후 true
    cookie.setAttribute("SameSite", "None");
    return cookie;
  }

  public Cookie remove(String key) {
    Cookie cookie = new Cookie(key, null);
    cookie.setMaxAge(0);
    cookie.setPath("/");
    cookie.setHttpOnly(true);
    cookie.setSecure(true); // TODO: https 적용후 true
    cookie.setAttribute("SameSite", "None");
    return cookie;
  }

  public Optional<Cookie> find(Cookie[] cookies, String key) {
    if (cookies == null || cookies.length == 0) return Optional.empty();
    return Arrays.stream(cookies)
        .filter(cookie -> Objects.equals(key, cookie.getName()))
        .findFirst();
  }
}
