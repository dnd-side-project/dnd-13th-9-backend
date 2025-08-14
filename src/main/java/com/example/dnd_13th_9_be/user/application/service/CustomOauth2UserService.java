package com.example.dnd_13th_9_be.user.application.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.example.dnd_13th_9_be.user.application.dto.KakaoAttribute;
import com.example.dnd_13th_9_be.user.application.dto.OAuth2Attribute;
import com.example.dnd_13th_9_be.user.application.dto.RoleAttribute;
import com.example.dnd_13th_9_be.user.application.model.UserModel;
import com.example.dnd_13th_9_be.user.application.repository.UserRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOauth2UserService extends DefaultOAuth2UserService {

  private final UserRepository userRepository;

  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    OAuth2User oAuth2User = super.loadUser(userRequest);

    String registrationId = userRequest.getClientRegistration().getRegistrationId();
    OAuth2Attribute oAuth2Attribute = getOAuth2Response(registrationId, oAuth2User.getAttributes());
    UserModel userModel = getOrCreateUser(oAuth2Attribute);

    Map<String, Object> modifiedAttributes = new HashMap<>(oAuth2User.getAttributes());
    modifiedAttributes.put("userId", userModel.getId());

    return new DefaultOAuth2User(
        Collections.singletonList(userModel.getRole()),
        modifiedAttributes,
        userRequest
            .getClientRegistration()
            .getProviderDetails()
            .getUserInfoEndpoint()
            .getUserNameAttributeName());
  }

  private OAuth2Attribute getOAuth2Response(String registrationId, Map<String, Object> attributes) {
    return switch (registrationId.toLowerCase()) {
      case "kakao" -> new KakaoAttribute(attributes);
      default -> throw new OAuth2AuthenticationException("지원하지 않는 OAuth2 제공자 입니다.");
    };
  }

  private UserModel getOrCreateUser(OAuth2Attribute oAuth2Attribute) {
    String providerId = oAuth2Attribute.getProviderId();
    log.info("getOrCreateUser 시작 - providerId: {}", providerId);

    return userRepository
        .findByProviderId(providerId)
        .map(
            existingUser -> {
              log.info("기존 사용자 로그인 - providerId: {}", providerId);
              return existingUser;
            })
        .orElseGet(
            () -> {
              log.info("신규 사용자 생성 시도 - providerId: {}", providerId);
              try {
                UserModel newUser =
                    UserModel.builder()
                        .providerId(providerId)
                        .name(oAuth2Attribute.getName())
                        .role(RoleAttribute.ROLE_USER)
                        .build();

                UserModel savedUser = userRepository.save(newUser);
                log.info("신규 사용자 생성 완료 - providerId: {}, ID: {}", providerId, savedUser.getId());
                return savedUser;

              } catch (Exception e) {
                log.warn("사용자 생성 실패, 재조회 시도 - providerId: {}, 에러: {}", providerId, e.getMessage());
                return userRepository
                    .findByProviderId(providerId)
                    .orElseThrow(() -> new RuntimeException("사용자 생성 및 조회 모두 실패: " + providerId));
              }
            });
  }
}
