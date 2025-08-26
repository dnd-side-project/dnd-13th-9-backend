package com.example.dnd_13th_9_be.placeMemo.application.dto;

import java.util.List;

import lombok.Builder;

import com.example.dnd_13th_9_be.placeMemo.application.model.PlaceMemoModel;
import com.example.dnd_13th_9_be.placeMemo.persistence.PlaceTag;
import io.swagger.v3.oas.annotations.media.Schema;

@Builder(toBuilder = true)
public record QueryPlaceMemoResponse(
    @Schema(description = " 주변 메모 ID", example = "1") Long id,
    @Schema(description = "장소 이름", example = "강남 카페") String title,
    @Schema(description = "장소 태그", example = "CONVENIENCE") PlaceTag placeTag,
    @Schema(description = "메모 내용", example = "조용하고 분위기 좋은 카페") String description,
    @Schema(description = "주소", example = "서울 강남구 테헤란로 123") String address,
    @Schema(description = "위도", example = "37.12345") String latitude,
    @Schema(description = "경도", example = "127.12345") String longitude,
    @Schema(description = "폴더 ID", example = "1") Long folderId,
    @Schema(description = "이미지 URL 리스트") List<String> imageUrls) {

  public static QueryPlaceMemoResponse from(PlaceMemoModel placeMemoModel) {
    return QueryPlaceMemoResponse.builder()
        .id(placeMemoModel.getId())
        .title(placeMemoModel.getTitle())
        .placeTag(placeMemoModel.getPlaceTag())
        .description(placeMemoModel.getDescription())
        .address(placeMemoModel.getAddress())
        .latitude(placeMemoModel.getLatitude())
        .longitude(placeMemoModel.getLongitude())
        .folderId(placeMemoModel.getFolderId())
        .imageUrls(
            placeMemoModel.getImageUrls() == null
                ? List.of()
                : List.copyOf(placeMemoModel.getImageUrls()))
        .build();
  }
}
