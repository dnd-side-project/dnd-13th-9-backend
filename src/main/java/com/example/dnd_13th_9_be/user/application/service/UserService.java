package com.example.dnd_13th_9_be.user.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.example.dnd_13th_9_be.global.error.UserNotFoundException;
import com.example.dnd_13th_9_be.user.application.dto.UserResponseDto;
import com.example.dnd_13th_9_be.user.application.model.UserModel;
import com.example.dnd_13th_9_be.user.application.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;

  @Transactional
  public UserResponseDto getCurrentUser(Long userId) {
    UserModel userModel = getUser(userId);
    return UserResponseDto.from(userModel);
  }

  private UserModel getUser(Long userId) {
    return userRepository
        .findById(userId)
        .orElseThrow(() -> new UserNotFoundException("존재하지 않는 사용자입니다 id: " + userId));
  }
}
