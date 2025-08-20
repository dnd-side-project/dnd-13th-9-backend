package com.example.dnd_13th_9_be.common.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserCreatedEvent {
  private final Long userId;
}
