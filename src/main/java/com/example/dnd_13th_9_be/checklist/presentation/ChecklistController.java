package com.example.dnd_13th_9_be.checklist.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import com.example.dnd_13th_9_be.checklist.application.service.ChecklistService;
import com.example.dnd_13th_9_be.checklist.presentation.docs.ChecklistDocs;
import com.example.dnd_13th_9_be.checklist.presentation.dto.ChecklistResponse;
import com.example.dnd_13th_9_be.global.response.ApiResponse;
import com.example.dnd_13th_9_be.user.application.dto.UserPrincipalDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/checklist")
public class ChecklistController implements ChecklistDocs {
  private final ChecklistService checklistService;

  @Override
  @GetMapping
  public ResponseEntity<ApiResponse<ChecklistResponse>> getChecklist(
      @AuthenticationPrincipal UserPrincipalDto userDetails) {
    ChecklistResponse data = checklistService.getChecklist(userDetails.getUserId());
    return ApiResponse.successEntity(data);
  }
}
