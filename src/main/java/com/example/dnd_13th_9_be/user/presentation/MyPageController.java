package com.example.dnd_13th_9_be.user.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import com.example.dnd_13th_9_be.global.response.ApiResponse;
import com.example.dnd_13th_9_be.user.application.dto.MyPageDto;
import com.example.dnd_13th_9_be.user.application.dto.UserPrincipalDto;
import com.example.dnd_13th_9_be.user.application.service.MyPageService;
import com.example.dnd_13th_9_be.user.presentation.docs.MyPageDocs;

@RestController
@RequestMapping("/api/mypage")
@RequiredArgsConstructor
public class MyPageController implements MyPageDocs {
  private final MyPageService myPageService;

  @Override
  @GetMapping
  public ResponseEntity<ApiResponse<MyPageDto>> getUserInfo(
      @AuthenticationPrincipal UserPrincipalDto userDetails) {
    return ApiResponse.successEntity(myPageService.findUserInfo(userDetails.getUserId()));
  }
}
