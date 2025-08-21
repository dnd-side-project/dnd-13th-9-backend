package com.example.dnd_13th_9_be.property.presentation;

import static com.example.dnd_13th_9_be.global.error.ErrorCode.PROPERTY_RECORD_IMAGE_LIMIT;

import com.example.dnd_13th_9_be.global.error.BusinessException;
import com.example.dnd_13th_9_be.global.response.ApiResponse;
import com.example.dnd_13th_9_be.property.application.PropertyRecordService;
import com.example.dnd_13th_9_be.property.application.model.PropertyRecordModel;
import com.example.dnd_13th_9_be.property.presentation.dto.request.PropertyRecordRequest;
import com.example.dnd_13th_9_be.user.application.dto.UserPrincipalDto;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/property")
public class PropertyRecordController {
    private final PropertyRecordService propertyRecordService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ApiResponse<PropertyRecordModel>> create(
        @AuthenticationPrincipal UserPrincipalDto userDetails,
        @RequestPart(value="image", required = false) List<MultipartFile> files,
        @Valid @RequestPart(value = "data") PropertyRecordRequest request
    ) {
        if (files != null && files.size() > 5) {
            throw new BusinessException(PROPERTY_RECORD_IMAGE_LIMIT);
        }

        var result = propertyRecordService.createPropertyRecord(userDetails.getUserId(), files, request);
        return ApiResponse.successEntity(result);
    }

    @DeleteMapping("/{propertyId}")
    public ResponseEntity<ApiResponse<Map<String, Object>>> delete(
        @AuthenticationPrincipal UserPrincipalDto userDetails,
        @PathVariable("propertyId") Long propertyId
    ) {
        propertyRecordService.deleteProperty(userDetails.getUserId(), propertyId);
        return ApiResponse.okEntity();
    }
}
