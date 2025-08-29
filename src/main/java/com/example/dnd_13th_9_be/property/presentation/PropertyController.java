package com.example.dnd_13th_9_be.property.presentation;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.example.dnd_13th_9_be.common.cipher.AESCipherUtil;
import com.example.dnd_13th_9_be.common.utils.JsonUtil;
import com.example.dnd_13th_9_be.global.response.ApiResponse;
import com.example.dnd_13th_9_be.property.application.PropertyService;
import com.example.dnd_13th_9_be.property.application.model.RecentPropertyModel;
import com.example.dnd_13th_9_be.property.presentation.docs.PropertyDocs;
import com.example.dnd_13th_9_be.property.presentation.dto.request.SharePropertyRequest;
import com.example.dnd_13th_9_be.property.presentation.dto.request.UpsertPropertyRequest;
import com.example.dnd_13th_9_be.property.presentation.dto.response.PropertyCreateResponse;
import com.example.dnd_13th_9_be.property.presentation.dto.response.PropertyDetailResponse;
import com.example.dnd_13th_9_be.property.presentation.dto.response.SharePropertyResponse;
import com.example.dnd_13th_9_be.user.application.dto.UserPrincipalDto;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/property")
public class PropertyController implements PropertyDocs {
  private final PropertyService propertyService;
  private final AESCipherUtil aesCipherUtil;

  @Override
  @PostMapping
  public ResponseEntity<ApiResponse<PropertyCreateResponse>> create(
      @AuthenticationPrincipal UserPrincipalDto userDetails,
      @RequestPart(value = "image", required = false) List<MultipartFile> files,
      @Validated @RequestPart(value = "data") UpsertPropertyRequest request) {
    if (files != null) {
      files.forEach(
          file -> {
            log.info(file.getOriginalFilename());
            log.info(String.valueOf(file.getSize()));
          });
    }

    Long propertyId = propertyService.createPropertyRecord(userDetails.getUserId(), files, request);
    return ApiResponse.successEntity(
        PropertyCreateResponse.builder().propertyId(propertyId).build());
  }

  @Override
  @DeleteMapping("/{propertyId}")
  public ResponseEntity<ApiResponse<Map<String, Object>>> delete(
      @AuthenticationPrincipal UserPrincipalDto userDetails,
      @PathVariable("propertyId") Long propertyId) {
    propertyService.deleteProperty(userDetails.getUserId(), propertyId);
    return ApiResponse.okEntity();
  }

  @Override
  @GetMapping("/{propertyId}")
  public ResponseEntity<ApiResponse<PropertyDetailResponse>> getProperty(
      @AuthenticationPrincipal UserPrincipalDto userDetails,
      @PathVariable("propertyId") Long propertyId) {
    PropertyDetailResponse response =
        propertyService.getProperty(userDetails.getUserId(), propertyId);
    return ApiResponse.successEntity(response);
  }

  @Override
  @PatchMapping("/{propertyId}")
  public ResponseEntity<ApiResponse<Map<String, Object>>> update(
      @AuthenticationPrincipal UserPrincipalDto userDetails,
      @PathVariable("propertyId") Long propertyId,
      @RequestPart(value = "image", required = false) List<MultipartFile> files,
      @Validated @RequestPart(value = "data") UpsertPropertyRequest request) {
    propertyService.updateProperty(userDetails.getUserId(), propertyId, files, request);

    return ApiResponse.okEntity();
  }

  @Override
  @GetMapping("/recent")
  public ResponseEntity<ApiResponse<List<RecentPropertyModel>>> getRecentProperties(
      @AuthenticationPrincipal UserPrincipalDto userDetails,
      @RequestParam(name = "size", defaultValue = "10") int size) {
    List<RecentPropertyModel> result =
        propertyService.findTopByUserId(userDetails.getUserId(), size);
    return ApiResponse.successEntity(result);
  }

  @Override
  @GetMapping("/sharelink/{propertyId}")
  public ResponseEntity<ApiResponse<SharePropertyResponse>> getShareLink(
      @AuthenticationPrincipal UserPrincipalDto userDetails,
      @PathVariable("propertyId") Long propertyId) {
    SharePropertyRequest request =
        SharePropertyRequest.builder()
            .userId(userDetails.getUserId())
            .propertyId(propertyId)
            .build();
    String requestString = JsonUtil.toJson(request);
    String encryptedString = aesCipherUtil.encode(requestString);
    return ApiResponse.successEntity(new SharePropertyResponse(encryptedString));
  }
}
