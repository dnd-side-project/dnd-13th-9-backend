package com.example.dnd_13th_9_be.user.application.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.example.dnd_13th_9_be.common.support.AbstractModel;
import com.example.dnd_13th_9_be.user.application.dto.RoleAttribute;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class UserModel implements AbstractModel {

  private Long id;
  private String name;
  private RoleAttribute role;
  private String providerId;
}
