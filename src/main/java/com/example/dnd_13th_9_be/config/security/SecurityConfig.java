package com.example.dnd_13th_9_be.config.security;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.example.dnd_13th_9_be.common.utils.CookieUtil;
import com.example.dnd_13th_9_be.user.application.service.CustomOauth2UserService;
import com.example.dnd_13th_9_be.user.application.token.JwtTokenValidatorService;

import static com.example.dnd_13th_9_be.user.application.dto.RoleAttribute.ROLE_USER;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final CustomAuthenticationEntryPoint authenticationEntryPoint;
  private final CustomAccessDeniedHandler customAccessDeniedHandler;
  private final JwtTokenValidatorService jwtTokenValidatorService;
  private final CookieUtil cookieUtil;

  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    return (web) ->
        web.ignoring()
            .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
            .requestMatchers("/h2-console/**", "/swagger-ui/**");
  }

  @Bean
  public SecurityFilterChain securityFilterChain(
      HttpSecurity http,
      CustomOauth2UserService customOauth2UserService,
      CustomSuccessHandler customSuccessHandler)
      throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)
        .cors(Customizer.withDefaults())
        .formLogin(AbstractHttpConfigurer::disable)
        .httpBasic(AbstractHttpConfigurer::disable)
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .oauth2Login(
            oauth2 ->
                oauth2
                    .userInfoEndpoint(userInfo -> userInfo.userService(customOauth2UserService))
                    .successHandler(customSuccessHandler))
        .addFilterAfter(
            new JWTFilter(authenticationEntryPoint, jwtTokenValidatorService, cookieUtil),
            OAuth2LoginAuthenticationFilter.class)
        .exceptionHandling(
            exception ->
                exception
                    .authenticationEntryPoint(authenticationEntryPoint)
                    .accessDeniedHandler(customAccessDeniedHandler))
        .authorizeHttpRequests(
            authorize ->
                authorize
                    .requestMatchers("/oauth2/**", "/login/oauth2/**", "/api/auth/refresh")
                    .permitAll()
                    .requestMatchers("/api/auth/my", "/api/auth/logout")
                    .hasAuthority(ROLE_USER.name())
                    .requestMatchers("/api/required-item/**", "/api/checklist")
                    .hasAuthority(ROLE_USER.name())
                    .anyRequest()
                    .authenticated());
    return http.build();
  }

  @Bean
  public WebMvcConfigurer addCorsMapping() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry
            .addMapping("/**")
            .allowedOriginPatterns("http://localhost:3000", "https://zipzip-home.vercel.app")
            .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
            .allowCredentials(true)
            .exposedHeaders("Set-Cookie")
            .maxAge(3600);
      }
    };
  }
}
