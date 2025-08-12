package com.example.dnd_13th_9_be.global.converter;

import jakarta.persistence.AttributeConverter;

public class BooleanAttributeConverter implements AttributeConverter<Boolean, Integer> {

  @Override
  public Integer convertToDatabaseColumn(Boolean attribute) {
    if (attribute) {
      return 1;
    } else {
      return 0;
    }
  }

  @Override
  public Boolean convertToEntityAttribute(Integer dbData) {
    return dbData == 1;
  }
}
