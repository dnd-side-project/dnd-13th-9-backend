package com.example.dnd_13th_9_be.plan.presentation;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import com.example.dnd_13th_9_be.global.response.ApiResponse;
import com.example.dnd_13th_9_be.plan.application.PlanService;
import com.example.dnd_13th_9_be.plan.presentation.dto.PlanSummaryResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/plan")
public class PlanController {
  private final PlanService planService;

  // TODO
  // 추후 Spring Security가 도입되면 tempUserId를 RequestParam이 아닌
  // @AuthenticationPrincipal로 부터 받아와야 함
  @GetMapping
  public ResponseEntity<ApiResponse<List<PlanSummaryResponse>>> getPlanList(
      @RequestParam("userId") Long tempUserId) {
    var results = planService.getPlanList(tempUserId);
    var data =
        results.stream()
            .map(r -> new PlanSummaryResponse(r.planId(), r.name(), r.createdAt(), r.folderCount()))
            .toList();
    return ApiResponse.successEntity(data);
  }
}
