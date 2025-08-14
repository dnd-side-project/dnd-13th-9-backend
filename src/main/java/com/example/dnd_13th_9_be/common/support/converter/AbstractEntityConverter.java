package com.example.dnd_13th_9_be.common.support.converter;

import com.example.dnd_13th_9_be.common.persistence.BaseEntity;
import com.example.dnd_13th_9_be.common.support.AbstractModel;

public interface AbstractEntityConverter<T extends BaseEntity, R extends AbstractModel> {
  R from(T t);

  T toEntity(R r);
}
