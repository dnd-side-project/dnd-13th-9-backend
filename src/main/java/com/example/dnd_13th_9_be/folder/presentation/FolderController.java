package com.example.dnd_13th_9_be.folder.presentation;

import java.util.List;
import java.util.Map;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
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
import com.example.dnd_13th_9_be.folder.presentation.dto.request.CreateFolderRequest;
import com.example.dnd_13th_9_be.folder.presentation.dto.request.RenameFolderRequest;
import com.example.dnd_13th_9_be.folder.presentation.dto.response.FolderDetailResponse;
import com.example.dnd_13th_9_be.folder.presentation.dto.response.FolderSummaryResponse;
import com.example.dnd_13th_9_be.global.response.ApiResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/folder")
public class FolderController {
  private final FolderService folderService;

  @GetMapping("/{planId}")
  public ResponseEntity<ApiResponse<List<FolderSummaryResponse>>> getFolderList(
      @PathVariable("planId") Long planId) {
    var results = folderService.getFolderList(planId);
    var data =
        results.stream()
            .map(
                r ->
                    new FolderSummaryResponse(
                        r.folderId(), r.name(), r.createdAt(), r.recordCount(), r.isDefaultFolder()))
            .toList();
    return ApiResponse.successEntity(data);
  }

  @PostMapping
  public ResponseEntity<ApiResponse<FolderDetailResponse>> create(
      @Valid @RequestBody CreateFolderRequest request) {
    var folderDetail = folderService.createFolder(request.planId(), request.name());
    var response =
        new FolderDetailResponse(
            folderDetail.folderId(), folderDetail.name(), folderDetail.createdAt(), folderDetail.isDefault());
    return ApiResponse.successEntity(response);
  }

  @PatchMapping("/{folderId}")
  public ResponseEntity<ApiResponse<Map<String, Object>>> rename(
      @PathVariable("folderId") Long folderId, @Valid @RequestBody RenameFolderRequest request) {
    folderService.renameFolder(folderId, request.name());
    return ApiResponse.okEntity();
  }

  @DeleteMapping("/{folderId}")
  public ResponseEntity<ApiResponse<Map<String, Object>>> delete(
      @PathVariable("folderId") Long folderId) {
    folderService.deleteFolder(folderId);
    return ApiResponse.okEntity();
  }
}
