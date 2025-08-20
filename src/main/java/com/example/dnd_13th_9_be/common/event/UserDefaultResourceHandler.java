package com.example.dnd_13th_9_be.common.event;


import com.example.dnd_13th_9_be.folder.application.FolderService;
import com.example.dnd_13th_9_be.plan.application.PlanService;
import com.example.dnd_13th_9_be.plan.application.dto.PlanDetailResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserDefaultResourceHandler {

    private final PlanService planService;
    private final FolderService folderService;

    @EventListener
    @Transactional
    public void createDefaultResources(UserCreatedEvent event){
        Long userId = event.getUserId();

        PlanDetailResult plan = planService.createDefaultPlan(userId);
        folderService.createDefaultFolder(plan.planId());
    }
}
