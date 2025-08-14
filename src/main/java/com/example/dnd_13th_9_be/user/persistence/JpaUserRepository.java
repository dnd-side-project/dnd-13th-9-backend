package com.example.dnd_13th_9_be.user.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserRepository extends JpaRepository<User, Long> {

  Optional<User> findByProviderId(String providerId);
}
