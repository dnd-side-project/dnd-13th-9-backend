package com.example.dnd_13th_9_be.user.application.dto;

import java.util.Map;

public class KakaoAttribute implements OAuth2Attribute {

  private final Map<String, Object> attribute;

  public KakaoAttribute(Map<String, Object> attribute) {
    this.attribute = attribute;
  }

  public static KakaoAttribute of(Map<String, Object> attribute) {
    return new KakaoAttribute(attribute);
  }

  @Override
  public String getProvider() {
    return "KAKAO";
  }

  @Override
  public String getProviderId() {
    return attribute.get("id").toString();
  }

  @Override
  public String getName() {
    Map<String, Object> kakaoAccount = (Map<String, Object>) attribute.get("kakao_account");
    if (kakaoAccount == null) return null;

    Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
    if (profile == null) return null;

    return (String) profile.get("nickname");
  }
}
