package com.example.dnd_13th_9_be.global.converter;

import jakarta.persistence.AttributeConverter;

public class BooleanAttributeConverter implements AttributeConverter<Boolean, Integer> {

  @Override
  public Integer convertToDatabaseColumn(Boolean attribute) {
    if (attribute == null) return 0;
    if (attribute) {
      return 1;
    } else {
      return 0;
    }
  }

  @Override
  public Boolean convertToEntityAttribute(Integer dbData) {
    if (dbData == null) return false;
    return dbData == 1;
  }
}
