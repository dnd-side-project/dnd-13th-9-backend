package com.example.dnd_13th_9_be.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {
  @Bean
  public OpenAPI openAPI() {
    Info info = new Info().title("zip.zip").version("v0.0.1").description("zip.zip 의 API 문서입니다.");

    return new OpenAPI().components(new Components()).info(info);
  }
}
