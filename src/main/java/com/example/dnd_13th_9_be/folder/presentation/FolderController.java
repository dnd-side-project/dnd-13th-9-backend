package com.example.dnd_13th_9_be.folder.presentation;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import com.example.dnd_13th_9_be.folder.application.FolderService;
import com.example.dnd_13th_9_be.folder.application.dto.FolderSummaryResult;
import com.example.dnd_13th_9_be.global.response.ApiResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/folder")
public class FolderController {
  private final FolderService folderService;

  @GetMapping("/{planId}")
  public ResponseEntity<ApiResponse<List<FolderSummaryResult>>> getFolderList(
      @PathVariable("planId") Long planId) {
    var results = folderService.getFolderList(planId);
    var data =
        results.stream()
            .map(
                r ->
                    new FolderSummaryResult(r.folderId(), r.name(), r.createdAt(), r.recordCount()))
            .toList();
    return ApiResponse.successEntity(data);
  }
}
