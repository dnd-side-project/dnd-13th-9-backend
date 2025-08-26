package com.example.dnd_13th_9_be.user.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.example.dnd_13th_9_be.checklist.application.repository.UserRequiredItemRepository;
import com.example.dnd_13th_9_be.global.error.BusinessException;
import com.example.dnd_13th_9_be.property.application.repository.PropertyRepository;
import com.example.dnd_13th_9_be.user.application.dto.MyPageDto;
import com.example.dnd_13th_9_be.user.application.repository.UserRepository;

import static com.example.dnd_13th_9_be.global.error.ErrorCode.USER_NOT_FOUND;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MyPageService {
  private final UserRepository userRepository;
  private final PropertyRepository propertyRepository;
  private final UserRequiredItemRepository userRequiredItemRepository;

  public MyPageDto findUserInfo(Long userId) {
    String userName =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new BusinessException(USER_NOT_FOUND))
            .getName();
    long propertyCount = propertyRepository.countByUserId(userId);
    long checklistCount = userRequiredItemRepository.countByUserId(userId);
    return MyPageDto.builder()
        .name(userName)
        .propertyCount(propertyCount)
        .checklistCount(checklistCount)
        .build();
  }
}
