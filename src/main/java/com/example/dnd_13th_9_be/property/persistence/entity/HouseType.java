package com.example.dnd_13th_9_be.property.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum HouseType {
  OFFICETEL("오피스텔"),
  ONEROOM("원룸"),
  APARTMENT("아파트"),
  VILLA("빌라"),
  COLIVING("코리빙"),
  GOSIWON("고시원"),
  BOARDING("하숙"),
  ETC("기타");

  private final String name;
}
