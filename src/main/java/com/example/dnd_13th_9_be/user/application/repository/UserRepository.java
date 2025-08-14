package com.example.dnd_13th_9_be.user.application.repository;

import java.util.Optional;

import com.example.dnd_13th_9_be.user.application.model.UserModel;

public interface UserRepository {

  Optional<UserModel> findById(Long userId);

  Optional<UserModel> findByProviderId(String providerId);

  UserModel save(UserModel userModel);

  boolean existsByProviderId(String providerId);
}
