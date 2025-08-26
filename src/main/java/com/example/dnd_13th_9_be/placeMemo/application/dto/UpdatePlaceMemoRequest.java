package com.example.dnd_13th_9_be.placeMemo.application.dto;

import java.util.List;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import com.example.dnd_13th_9_be.placeMemo.application.model.PlaceMemoModel;
import com.example.dnd_13th_9_be.placeMemo.persistence.PlaceTag;
import io.swagger.v3.oas.annotations.media.Schema;

public record UpdatePlaceMemoRequest(
    @Schema(description = "장소 이름", example = "강남 카페") @NotBlank @Size(max = 10) String title,
    @Schema(description = "장소 태그", example = "CONVENIENCE") @NotNull PlaceTag placeTag,
    @Schema(description = "메모 내용", example = "조용하고 분위기 좋은 카페") @Size(max = 80) String description,
    @Schema(description = "주소", example = "서울 강남구 테헤란로 123") @NotBlank @Size(max = 200)
        String address,
    @Schema(description = "위도", example = "37.12345") @NotBlank String latitude,
    @Schema(description = "경도", example = "127.12345") @NotBlank String longitude,
    @Schema(description = "폴더 ID", example = "1") @NotNull Long folderId,
    @Schema(description = "최종 상태에 유지할 기존 이미지 URL(모두)", example = "[\"https://.../a.png\"]")
        List<String> existingImageUrls,
    @Schema(description = "최종 상태에 추가할 새 이미지 파일(선택, 최대 5개)") @Size(max = 5)
        List<MultipartFile> newImages) {

  public PlaceMemoModel toModel(final List<String> finalImageUrls) {
    return PlaceMemoModel.builder()
        .title(title)
        .placeTag(placeTag)
        .description(description)
        .address(address)
        .latitude(latitude)
        .longitude(longitude)
        .folderId(folderId)
        .imageUrls(finalImageUrls == null ? List.of() : List.copyOf(finalImageUrls))
        .build();
  }
}
