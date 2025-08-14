package com.example.dnd_13th_9_be.user.persistence;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import lombok.AllArgsConstructor;

import com.example.dnd_13th_9_be.user.application.model.UserModel;
import com.example.dnd_13th_9_be.user.application.model.converter.UserConverter;
import com.example.dnd_13th_9_be.user.application.repository.UserRepository;

@Repository
@AllArgsConstructor
public class UserRepositoryImpl implements UserRepository {
  private final JpaUserRepository jpaUserRepository;
  private final UserConverter userConverter;

  @Override
  public Optional<UserModel> findById(Long userId) {
    return jpaUserRepository.findById(userId).map(userConverter::from);
  }

  @Override
  public Optional<UserModel> findByProviderId(String providerId) {
    return jpaUserRepository.findByProviderId(providerId).map(userConverter::from);
  }

  @Override
  public UserModel save(UserModel userModel) {
    User user = userConverter.toEntity(userModel);
    User savedUser = jpaUserRepository.save(user);
    return userConverter.from(savedUser);
  }
}
