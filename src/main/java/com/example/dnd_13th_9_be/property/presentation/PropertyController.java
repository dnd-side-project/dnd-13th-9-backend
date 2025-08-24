package com.example.dnd_13th_9_be.property.presentation;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

import com.example.dnd_13th_9_be.global.response.ApiResponse;
import com.example.dnd_13th_9_be.property.application.PropertyService;
import com.example.dnd_13th_9_be.property.presentation.dto.request.UpsertPropertyRequest;
import com.example.dnd_13th_9_be.property.presentation.dto.response.PropertyDetailResponse;
import com.example.dnd_13th_9_be.user.application.dto.UserPrincipalDto;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/property")
public class PropertyController {
  private final PropertyService propertyService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<ApiResponse<Map<String, Object>>> create(
      @AuthenticationPrincipal UserPrincipalDto userDetails,
      @RequestPart(value = "image", required = false) List<MultipartFile> files,
      @RequestPart(value = "data") UpsertPropertyRequest request) {
    propertyService.createPropertyRecord(userDetails.getUserId(), files, request);
    return ApiResponse.okEntity();
  }

  @DeleteMapping("/{propertyId}")
  public ResponseEntity<ApiResponse<Map<String, Object>>> delete(
      @AuthenticationPrincipal UserPrincipalDto userDetails,
      @PathVariable("propertyId") Long propertyId) {
    propertyService.deleteProperty(userDetails.getUserId(), propertyId);
    return ApiResponse.okEntity();
  }

  @GetMapping("/{propertyId}")
  public ResponseEntity<ApiResponse<PropertyDetailResponse>> getProperty(
      @AuthenticationPrincipal UserPrincipalDto userDetails,
      @PathVariable("propertyId") Long propertyId) {
    PropertyDetailResponse response = propertyService.getProperty(userDetails.getUserId(), propertyId);
    return ApiResponse.successEntity(response);
  }

  @PatchMapping("/{propertyId}")
  public ResponseEntity<ApiResponse<Map<String, Object>>> update(
      @AuthenticationPrincipal UserPrincipalDto userDetails,
      @PathVariable("propertyId") Long propertyId,
      @RequestPart(value = "image", required = false) List<MultipartFile> files,
      @Validated @RequestPart(value = "data") UpsertPropertyRequest request) {
    propertyService.updateProperty(userDetails.getUserId(), propertyId, files, request);

    return ApiResponse.okEntity();
  }
}
