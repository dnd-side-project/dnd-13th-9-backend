package com.example.dnd_13th_9_be.checklist.presentation;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import com.example.dnd_13th_9_be.checklist.application.service.UserRequiredItemService;
import com.example.dnd_13th_9_be.global.response.ApiResponse;
import com.example.dnd_13th_9_be.user.application.dto.UserPrincipalDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/required-item")
public class UserRequiredItemController {
  private final UserRequiredItemService userRequiredItemService;

  @PutMapping("/{itemId}")
  public ResponseEntity<ApiResponse<Map<String, Object>>> add(
      @AuthenticationPrincipal UserPrincipalDto userDetails, @PathVariable("itemId") Long itemId) {
    userRequiredItemService.add(userDetails.getUserId(), itemId);
    return ApiResponse.okEntity();
  }
}
