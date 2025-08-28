package com.example.dnd_13th_9_be.common.utils;

import com.example.dnd_13th_9_be.global.error.BusinessException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import static com.example.dnd_13th_9_be.global.error.ErrorCode.JSON_PROCESSING_ERROR;

public class JsonUtil {
  private static final ObjectMapper objectMapper = new ObjectMapper();

  public static String toJson(Object obj) {
    try {
      return objectMapper.writeValueAsString(obj);
    } catch (JsonProcessingException e) {
      throw new BusinessException(JSON_PROCESSING_ERROR);
    }
  }

  public static <T> T fromJson(String json, Class<T> clazz) {
    try {
      return objectMapper.readValue(json, clazz);
    } catch (JsonProcessingException e) {
      throw new BusinessException(JSON_PROCESSING_ERROR);
    }
  }
}
