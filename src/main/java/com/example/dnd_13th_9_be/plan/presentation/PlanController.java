package com.example.dnd_13th_9_be.plan.presentation;

import java.util.List;
import java.util.Map;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import com.example.dnd_13th_9_be.global.response.ApiResponse;
import com.example.dnd_13th_9_be.plan.application.PlanService;
import com.example.dnd_13th_9_be.plan.presentation.dto.request.CreatePlanRequest;
import com.example.dnd_13th_9_be.plan.presentation.dto.request.RenamePlanRequest;
import com.example.dnd_13th_9_be.plan.presentation.dto.response.PlanDetailResponse;
import com.example.dnd_13th_9_be.plan.presentation.dto.response.PlanSummaryResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/plan")
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
            .map(
                r ->
                    new PlanSummaryResponse(
                        r.planId(), r.name(), r.createdAt(), r.folderCount(), r.isDefault()))
            .toList();
    return ApiResponse.successEntity(data);
  }

  @PostMapping
  public ResponseEntity<ApiResponse<PlanDetailResponse>> create(
      @RequestParam("userId") Long tempUserId, @Valid @RequestBody CreatePlanRequest request) {
    var planDetail = planService.createPlan(tempUserId, request.name());
    var response =
        new PlanDetailResponse(planDetail.planId(), planDetail.name(), planDetail.createdAt(), planDetail.isDefault());
    return ApiResponse.successEntity(response);
  }

  @PatchMapping("/{planId}")
  public ResponseEntity<ApiResponse<Map<String, Object>>> rename(
      @PathVariable("planId") Long planId, @Valid @RequestBody RenamePlanRequest request) {
    planService.renamePlan(planId, request.name());
    return ApiResponse.okEntity();
  }

  @DeleteMapping("/{planId}")
  public ResponseEntity<ApiResponse<Map<String, Object>>> delete(
      @PathVariable("planId") Long planId) {
    planService.deletePlan(planId);
    return ApiResponse.okEntity();
  }
}
