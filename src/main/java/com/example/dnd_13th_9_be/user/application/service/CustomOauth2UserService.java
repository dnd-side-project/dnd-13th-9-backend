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

  private UserModel getOrCreateUser(OAuth2Attribute attr) {
    return userRepository
        .findByProviderId(attr.getProviderId())
        .map(
            user -> {
              UserModel updated = user.toBuilder().name(attr.getName()).build();
              return userRepository.save(updated);
            })
        .orElseGet(
            () -> {
              UserModel newUser =
                  UserModel.builder()
                      .providerId(attr.getProviderId())
                      .name(attr.getName())
                      .role(RoleAttribute.ROLE_USER)
                      .build();
              return userRepository.save(newUser);
            });
  }
}
