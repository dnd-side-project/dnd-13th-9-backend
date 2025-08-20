package com.example.dnd_13th_9_be.plan.presentation;

import java.util.List;
import java.util.Map;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import com.example.dnd_13th_9_be.global.response.ApiResponse;
import com.example.dnd_13th_9_be.plan.application.PlanService;
import com.example.dnd_13th_9_be.plan.presentation.docs.PlanDocs;
import com.example.dnd_13th_9_be.plan.presentation.dto.request.CreatePlanRequest;
import com.example.dnd_13th_9_be.plan.presentation.dto.request.RenamePlanRequest;
import com.example.dnd_13th_9_be.plan.presentation.dto.response.PlanDetailResponse;
import com.example.dnd_13th_9_be.plan.presentation.dto.response.PlanSummaryResponse;
import com.example.dnd_13th_9_be.user.application.dto.UserPrincipalDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/plan")
public class PlanController implements PlanDocs {
  private final PlanService planService;

  @Override
  @GetMapping
  public ResponseEntity<ApiResponse<List<PlanSummaryResponse>>> getPlanList(
      @AuthenticationPrincipal UserPrincipalDto userDetails) {
    var results = planService.getPlanList(userDetails.getUserId());
    var data =
        results.stream()
            .map(
                r ->
                    new PlanSummaryResponse(
                        r.planId(), r.name(), r.createdAt(), r.folderCount(), r.isDefault()))
            .toList();
    return ApiResponse.successEntity(data);
  }

  @Override
  @PostMapping
  public ResponseEntity<ApiResponse<PlanDetailResponse>> create(
      @AuthenticationPrincipal UserPrincipalDto userDetails,
      @Valid @RequestBody CreatePlanRequest request) {
    var planDetail = planService.createPlan(userDetails.getUserId(), request.name());
    var response =
        new PlanDetailResponse(
            planDetail.planId(), planDetail.name(), planDetail.createdAt(), planDetail.isDefault());
    return ApiResponse.successEntity(response);
  }

  @Override
  @PatchMapping("/{planId}")
  public ResponseEntity<ApiResponse<Map<String, Object>>> rename(
      @AuthenticationPrincipal UserPrincipalDto userDetails,
      @PathVariable("planId") Long planId,
      @Valid @RequestBody RenamePlanRequest request) {
    planService.renamePlan(userDetails.getUserId(), planId, request.name());
    return ApiResponse.okEntity();
  }

  @Override
  @DeleteMapping("/{planId}")
  public ResponseEntity<ApiResponse<Map<String, Object>>> delete(
      @AuthenticationPrincipal UserPrincipalDto userDetails, @PathVariable("planId") Long planId) {
    planService.deletePlan(userDetails.getUserId(), planId);
    return ApiResponse.okEntity();
  }
}
