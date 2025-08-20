package com.example.dnd_13th_9_be.common.event;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.example.dnd_13th_9_be.folder.application.FolderService;
import com.example.dnd_13th_9_be.global.error.BusinessException;
import com.example.dnd_13th_9_be.global.error.ErrorCode;
import com.example.dnd_13th_9_be.plan.application.PlanService;
import com.example.dnd_13th_9_be.plan.application.dto.PlanDetailResult;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserDefaultResourceHandler {

  private final PlanService planService;
  private final FolderService folderService;

  @Async
  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  public void createDefaultResources(UserCreatedEvent event) {
    Long userId = event.getUserId();

    try {
      PlanDetailResult plan = planService.createDefaultPlan(userId);
      folderService.createDefaultFolder(userId, plan.planId());
    } catch (Exception e) {
      throw new BusinessException(ErrorCode.DEFAULT_FOLDER_PLAN_NOT_CREATED);
    }
  }
}
