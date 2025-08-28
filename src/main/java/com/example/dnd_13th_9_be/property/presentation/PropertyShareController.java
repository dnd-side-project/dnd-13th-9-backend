package com.example.dnd_13th_9_be.property.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import com.example.dnd_13th_9_be.common.cipher.AESCipherUtil;
import com.example.dnd_13th_9_be.common.utils.JsonUtil;
import com.example.dnd_13th_9_be.global.response.ApiResponse;
import com.example.dnd_13th_9_be.property.application.PropertyService;
import com.example.dnd_13th_9_be.property.presentation.dto.request.SharePropertyRequest;
import com.example.dnd_13th_9_be.property.presentation.dto.response.PropertyDetailResponse;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/property/share")
public class PropertyShareController {
  private final PropertyService propertyService;
  private final AESCipherUtil aesCipherUtil;

  @GetMapping("/{shareLink}")
  public ResponseEntity<ApiResponse<PropertyDetailResponse>> getProperty(
      @PathVariable("shareLink") String shareLink) {
    String decrypted = aesCipherUtil.decode(shareLink);
    SharePropertyRequest request = JsonUtil.fromJson(decrypted, SharePropertyRequest.class);
    PropertyDetailResponse response =
        propertyService.getProperty(request.userId(), request.propertyId());
    return ApiResponse.successEntity(response);
  }
}
