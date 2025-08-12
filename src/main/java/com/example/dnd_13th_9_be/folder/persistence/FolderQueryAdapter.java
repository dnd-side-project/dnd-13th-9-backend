package com.example.dnd_13th_9_be.folder.persistence;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import com.example.dnd_13th_9_be.folder.application.FolderQueryPort;
import com.example.dnd_13th_9_be.folder.application.dto.FolderSummaryResult;

@Component
@RequiredArgsConstructor
public class FolderQueryAdapter implements FolderQueryPort {
  private final FolderRepository folderRepository;

  @Override
  public List<FolderSummaryResult> findSummariesByPlanId(Long planId) {
    return folderRepository.findSummariesByPlanId(planId).stream()
        .map(
            r ->
                new FolderSummaryResult(
                    r.id(),
                    r.name(),
                    r.createdAt(),
                    r.locationRecordCount() + r.propertyRecordCount()))
        .toList();
  }
}
