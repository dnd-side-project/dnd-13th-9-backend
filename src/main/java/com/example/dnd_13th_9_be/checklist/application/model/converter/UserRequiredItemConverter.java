package com.example.dnd_13th_9_be.checklist.application.model.converter;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import com.example.dnd_13th_9_be.checklist.application.model.UserRequiredItemModel;
import com.example.dnd_13th_9_be.checklist.persistence.entity.UserRequiredItem;
import com.example.dnd_13th_9_be.common.support.converter.AbstractEntityConverter;
import com.example.dnd_13th_9_be.user.application.model.converter.UserConverter;

@Component
@RequiredArgsConstructor
public class UserRequiredItemConverter
    implements AbstractEntityConverter<UserRequiredItem, UserRequiredItemModel> {

  private final UserConverter userConverter;
  private final ChecklistItemConverter itemConverter;

  @Override
  public UserRequiredItemModel from(UserRequiredItem e) {
    return UserRequiredItemModel.builder()
        .id(e.getId())
        .userId(e.getUser().getId())
        .itemId(e.getItem().getId())
        .build();
  }

  @Override
  public UserRequiredItem toEntity(UserRequiredItemModel u) {
    throw new UnsupportedOperationException("Use persistence layer repository with getReference()");
  }
}
