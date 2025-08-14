package com.example.dnd_13th_9_be.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "app")
public class AppProperties {
  private Security security;

  @Setter
  @Getter
  public static class Security {
    private Jwt jwt;
    private Oauth2 oauth2;

    @Setter
    @Getter
    public static class Jwt {
      private String secret;
      private long accessTokenExpirationMs;
      private long refreshTokenExpirationMs;
    }

    @Setter
    @Getter
    public static class Oauth2 {
      private String frontendRedirectBaseUrl;
    }
  }
}
