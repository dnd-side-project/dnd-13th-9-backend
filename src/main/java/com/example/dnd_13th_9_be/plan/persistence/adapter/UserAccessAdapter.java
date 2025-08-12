package com.example.dnd_13th_9_be.plan.persistence.adapter;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import com.example.dnd_13th_9_be.plan.application.port.UserAccessPort;
import com.example.dnd_13th_9_be.user.persistence.UserRepository;

@Component
@RequiredArgsConstructor
public class UserAccessAdapter implements UserAccessPort {
  private final UserRepository userRepository;

  @Override
  public boolean existsById(Long userId) {
    return userRepository.existsById(userId);
  }
}
