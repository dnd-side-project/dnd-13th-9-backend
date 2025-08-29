package com.example.dnd_13th_9_be.placeMemo.presentation;

import jakarta.validation.Valid;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import com.example.dnd_13th_9_be.global.response.ApiResponse;
import com.example.dnd_13th_9_be.placeMemo.application.dto.*;
import com.example.dnd_13th_9_be.placeMemo.application.service.PlaceMemoService;
import com.example.dnd_13th_9_be.placeMemo.presentation.docs.PlaceMemoDocs;
import com.example.dnd_13th_9_be.user.application.dto.UserPrincipalDto;

@Slf4j
@RestController
@RequestMapping("api/placeMemo")
@RequiredArgsConstructor
public class PlaceMemoController implements PlaceMemoDocs {

  private final PlaceMemoService placeMemoService;

  @PostMapping
  public ResponseEntity<ApiResponse<CreatePlaceMemoResponse>> create(
      @AuthenticationPrincipal UserPrincipalDto userPrincipalDto,
      @Valid @ModelAttribute CreatePlaceMemoRequest request) {
    request.images().forEach(image -> {
      log.info("요청된 이미지 이름 : {}", image.getOriginalFilename());
    });
    CreatePlaceMemoResponse response =
        placeMemoService.create(userPrincipalDto.getUserId(), request);
    return ApiResponse.successEntity(response);
  }

  @GetMapping("/{placeMemoId}")
  public ResponseEntity<ApiResponse<QueryPlaceMemoResponse>> findOne(
      @AuthenticationPrincipal UserPrincipalDto userPrincipalDto,
      @PathVariable("placeMemoId") Long placeMemoId) {
    QueryPlaceMemoResponse response =
        placeMemoService.findOne(placeMemoId, userPrincipalDto.getUserId());
    return ApiResponse.successEntity(response);
  }

  @DeleteMapping("/{placeMemoId}")
  public ResponseEntity<ApiResponse<String>> delete(
      @AuthenticationPrincipal UserPrincipalDto userPrincipalDto,
      @PathVariable("placeMemoId") Long placeMemoId) {
    placeMemoService.delete(placeMemoId, userPrincipalDto.getUserId());
    return ApiResponse.successEntity("삭제되었습니다");
  }

  @PutMapping("/{placeMemoId}")
  public ResponseEntity<ApiResponse<String>> update(
      @AuthenticationPrincipal UserPrincipalDto userPrincipalDto,
      @PathVariable("placeMemoId") Long placeMemoId,
      @Valid @ModelAttribute UpdatePlaceMemoRequest request) {
    placeMemoService.update(placeMemoId, userPrincipalDto.getUserId(), request);
    return ApiResponse.successEntity("수정완료");
  }

  @GetMapping("/folders/{folderId}")
  public ResponseEntity<ApiResponse<QueryPlaceMemoListResponse>> findAll(
      @AuthenticationPrincipal UserPrincipalDto userPrincipalDto,
      @PathVariable("folderId") Long folderId) {
    QueryPlaceMemoListResponse response =
        placeMemoService.findAll(folderId, userPrincipalDto.getUserId());
    return ApiResponse.successEntity(response);
  }
}
