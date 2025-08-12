package com.example.dnd_13th_9_be.folder.application.port;

public interface PlanAccessPort {
  // TODO: Spring Security 작업시 userId로 소유권 검사 추가
  boolean existsById(Long planId);
}
