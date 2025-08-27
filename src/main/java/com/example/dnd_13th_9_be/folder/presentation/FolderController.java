package com.example.dnd_13th_9_be.folder.presentation;

import java.util.List;
import java.util.Map;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import com.example.dnd_13th_9_be.folder.application.FolderService;
import com.example.dnd_13th_9_be.folder.presentation.docs.FolderDocs;
import com.example.dnd_13th_9_be.folder.presentation.dto.request.CreateFolderRequest;
import com.example.dnd_13th_9_be.folder.presentation.dto.request.RenameFolderRequest;
import com.example.dnd_13th_9_be.folder.presentation.dto.response.FolderDetailResponse;
import com.example.dnd_13th_9_be.folder.presentation.dto.response.FolderSummaryResponse;
import com.example.dnd_13th_9_be.folder.presentation.dto.response.QueryFolderMemoListResponse;
import com.example.dnd_13th_9_be.folder.presentation.dto.response.RecordSummaryResponse;
import com.example.dnd_13th_9_be.global.response.ApiResponse;
import com.example.dnd_13th_9_be.user.application.dto.UserPrincipalDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/folder")
public class FolderController implements FolderDocs {
  private final FolderService folderService;

  @Override
  @GetMapping("/{planId}")
  public ResponseEntity<ApiResponse<List<FolderSummaryResponse>>> getFolderList(
      @AuthenticationPrincipal UserPrincipalDto userDetails, @PathVariable("planId") Long planId) {
    var results = folderService.getFolderList(userDetails.getUserId(), planId);
    var data = results.stream().map(FolderSummaryResponse::from).toList();
    return ApiResponse.successEntity(data);
  }

  @Override
  @PostMapping
  public ResponseEntity<ApiResponse<FolderDetailResponse>> create(
      @AuthenticationPrincipal UserPrincipalDto userDetails,
      @Valid @RequestBody CreateFolderRequest request) {
    var folderDetail =
        folderService.createFolder(userDetails.getUserId(), request.planId(), request.name());
    var response = FolderDetailResponse.from(folderDetail);
    return ApiResponse.successEntity(response);
  }

  @Override
  @PatchMapping("/{folderId}")
  public ResponseEntity<ApiResponse<Map<String, Object>>> rename(
      @AuthenticationPrincipal UserPrincipalDto userDetails,
      @PathVariable("folderId") Long folderId,
      @Valid @RequestBody RenameFolderRequest request) {
    folderService.renameFolder(userDetails.getUserId(), folderId, request.name());
    return ApiResponse.okEntity();
  }

  @Override
  @DeleteMapping("/{folderId}")
  public ResponseEntity<ApiResponse<Map<String, Object>>> delete(
      @AuthenticationPrincipal UserPrincipalDto userDetails,
      @PathVariable("folderId") Long folderId) {
    folderService.deleteFolder(userDetails.getUserId(), folderId);
    return ApiResponse.okEntity();
  }

  @Override
  @GetMapping("/{folderId}/records") // 매물 메모 리스트 조회
  public ResponseEntity<ApiResponse<List<RecordSummaryResponse>>> getRecordList(
      @AuthenticationPrincipal UserPrincipalDto userDetails,
      @PathVariable("folderId") Long folderId) {
    List<RecordSummaryResponse> result =
        folderService.getRecordList(userDetails.getUserId(), folderId).stream()
            .map(RecordSummaryResponse::from)
            .toList();
    return ApiResponse.successEntity(result);
  }

  @GetMapping("/{folderId}/memos") // 메물 메모 + 주변 장소 메모 조회
  public ResponseEntity<ApiResponse<QueryFolderMemoListResponse>> findAll(
      @AuthenticationPrincipal UserPrincipalDto userPrincipalDto,
      @PathVariable("folderId") Long folderId) {
    QueryFolderMemoListResponse response =
        folderService.findAll(folderId, userPrincipalDto.getUserId());
    return ApiResponse.successEntity(response);
  }
}
