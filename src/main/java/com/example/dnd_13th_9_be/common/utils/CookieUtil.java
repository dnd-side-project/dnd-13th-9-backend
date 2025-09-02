package com.example.dnd_13th_9_be.common.utils;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import jakarta.servlet.http.Cookie;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CookieUtil {

  @Value("${cookie.secure:false}")
  private boolean secure;

  @Value("${cookie.same-site:Lax}")
  private String sameSite;

  @Value("${cookie.domain:}")
  private String domain;

  public Cookie create(String key, String value, long expirationMs) {
    Cookie cookie = new Cookie(key, value);
    cookie.setMaxAge((int) (expirationMs / 1000)); // 초 단위로 변환
    cookie.setPath("/");
    cookie.setHttpOnly(true);
    cookie.setSecure(secure);
    cookie.setAttribute("SameSite", sameSite);
    if(!domain.isEmpty()){
      cookie.setDomain(domain);
    }
    return cookie;
  }

  public Cookie remove(String key) {
    Cookie cookie = new Cookie(key, null);
    cookie.setMaxAge(0);
    cookie.setPath("/");
    cookie.setHttpOnly(true);
    cookie.setSecure(secure);
    cookie.setAttribute("SameSite", sameSite);
    if(!domain.isEmpty()){
      cookie.setDomain(domain);
    }
    return cookie;
  }

  public Optional<Cookie> find(Cookie[] cookies, String key) {
    if (cookies == null || cookies.length == 0) return Optional.empty();
    return Arrays.stream(cookies)
            .filter(cookie -> Objects.equals(key, cookie.getName()))
            .findFirst();
  }
}
