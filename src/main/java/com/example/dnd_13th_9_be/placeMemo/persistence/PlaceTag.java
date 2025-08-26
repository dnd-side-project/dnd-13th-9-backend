package com.example.dnd_13th_9_be.placeMemo.persistence;

import java.util.Arrays;

import com.example.dnd_13th_9_be.global.error.BusinessException;
import com.example.dnd_13th_9_be.global.error.ErrorCode;

public enum PlaceTag {
  ADVANTAGE("장점"),
  DISADVANTAGE("단점"),
  CONVENIENCE("편의 시설"),
  TRANSPORTATION("교통"),
  SECURITY("치안"),
  NOISE("소음");

  private final String tag;

  PlaceTag(String tag) {
    this.tag = tag;
  }

  public String getTag() {
    return tag;
  }

  public static PlaceTag find(String tag) {
    return Arrays.stream(values())
        .filter(placeTag -> placeTag.getTag().equals(tag))
        .findAny()
        .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_PLACETAG));
  }
}
