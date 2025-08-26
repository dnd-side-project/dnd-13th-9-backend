package com.example.dnd_13th_9_be.placeMemo.presentation.docs;

import com.example.dnd_13th_9_be.global.response.ApiResponse;
import com.example.dnd_13th_9_be.placeMemo.application.dto.*;
import com.example.dnd_13th_9_be.user.application.dto.UserPrincipalDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "주변장소메모", description = "주변장소(PlaceMemo) 관련 API")
public interface PlaceMemoDocs {
    @Operation(
            summary = "장소 메모 생성",
            description = "이미지 파일을 포함한 장소 메모를 생성합니다.",
            requestBody = @RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                            schema = @Schema(implementation = CreatePlaceMemoRequest.class)
                    )
            )
    )
    ResponseEntity<ApiResponse<CreatePlaceMemoResponse>> create(
            @Parameter(hidden = true) @AuthenticationPrincipal UserPrincipalDto user,
            @ModelAttribute @Valid CreatePlaceMemoRequest request
    );

    @Operation(
            summary = "장소 메모 단건 조회",
            description = "placeMemoId에 해당하는 장소 메모를 조회합니다."
    )
    ResponseEntity<ApiResponse<QueryPlaceMemoResponse>> findOne(
            @Parameter(hidden = true) @AuthenticationPrincipal UserPrincipalDto user,
            @Parameter(description = "장소 메모 ID", required = true, example = "10")
            @PathVariable("placeMemoId") Long placeMemoId
    );

    @Operation(
            summary = "장소 메모 삭제",
            description = "placeMemoId에 해당하는 장소 메모를 삭제합니다. (이미지 파일도 함께 정리)"
    )
    ResponseEntity<ApiResponse<String>> delete(
            @Parameter(hidden = true) @AuthenticationPrincipal UserPrincipalDto user,
            @Parameter(description = "장소 메모 ID", required = true, example = "10")
            @PathVariable("placeMemoId") Long placeMemoId
    );

    @Operation(
            summary = "장소 메모 수정 (PUT 전체 교체)",
            description = """
            폼데이터로 전체 값을 전송해 최종 상태로 교체합니다.
            - existingImageUrls: 유지할 기존 이미지 URL 전부
            - newImages: 추가 업로드할 파일들
            """,
            requestBody = @RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                            schema = @Schema(implementation = UpdatePlaceMemoRequest.class)
                    )
            )
    )
    ResponseEntity<ApiResponse<String>> update(
            @Parameter(hidden = true) @AuthenticationPrincipal UserPrincipalDto user,
            @Parameter(description = "장소 메모 ID", required = true, example = "10")
            @PathVariable("placeMemoId") Long placeMemoId,
            @ModelAttribute @Valid UpdatePlaceMemoRequest request
    );

    @Operation(
            summary = "폴더 내 장소 메모 목록 조회",
            description = "특정 폴더(folderId)에 속한 장소 메모들을 조회합니다. (최대 10개)"
    )
    ResponseEntity<ApiResponse<QueryPlaceMemoListResponse>> findAll(
            @Parameter(hidden = true) @AuthenticationPrincipal UserPrincipalDto user,
            @Parameter(description = "폴더 ID", required = true, example = "1")
            @PathVariable("folderId") Long folderId
    );
}
