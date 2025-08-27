package com.example.dnd_13th_9_be.folder.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.example.dnd_13th_9_be.folder.application.dto.FolderDetailResult;
import com.example.dnd_13th_9_be.folder.application.dto.FolderSummaryResult;
import com.example.dnd_13th_9_be.folder.application.dto.RecordSummaryResult;
import com.example.dnd_13th_9_be.folder.application.repository.FolderRepository;
import com.example.dnd_13th_9_be.folder.presentation.dto.response.QueryFolderMemoListResponse;
import com.example.dnd_13th_9_be.folder.presentation.dto.response.RecordSummaryResponse;
import com.example.dnd_13th_9_be.global.error.BusinessException;
import com.example.dnd_13th_9_be.placeMemo.application.dto.QueryPlaceMemoResponse;
import com.example.dnd_13th_9_be.placeMemo.application.repository.PlaceMemoRepository;
import com.example.dnd_13th_9_be.plan.application.repository.PlanRepository;

import static com.example.dnd_13th_9_be.global.error.ErrorCode.DEFAULT_FOLDER_CANNOT_BE_DELETE;
import static com.example.dnd_13th_9_be.global.error.ErrorCode.FOLDER_CREATION_LIMIT;
import static com.example.dnd_13th_9_be.global.error.ErrorCode.FOLDER_DELETE_FAILED;
import static com.example.dnd_13th_9_be.global.error.ErrorCode.FOLDER_RENAME_FAILED;

@Service
@RequiredArgsConstructor
public class FolderService {
  private final FolderRepository folderRepository;
  private final PlanRepository planRepository;

  private static final String DEFAULT_FOLDER_NAME = "기본 폴더";

  private final PlaceMemoRepository placeMemoRepository;

  @Transactional
  public void createDefaultFolder(Long userId, Long planId) {
    folderRepository.create(userId, planId, DEFAULT_FOLDER_NAME, true);
  }

  @Transactional(readOnly = true)
  public List<FolderSummaryResult> getFolderList(Long userId, Long planId) {
    planRepository.verifyExistsById(userId, planId);
    return folderRepository.findSummariesByPlanId(userId, planId);
  }

  @Transactional
  public FolderDetailResult createFolder(Long userId, Long planId, String name) {
    planRepository.verifyExistsById(userId, planId);

    boolean isFolderLimitExceed = folderRepository.countByPlanId(planId) >= 10;
    if (isFolderLimitExceed) {
      throw new BusinessException(FOLDER_CREATION_LIMIT);
    }

    return folderRepository.create(userId, planId, name, false);
  }

  @Transactional
  public void renameFolder(Long userId, Long folderId, String name) {
    folderRepository.verifyById(userId, folderId);
    boolean isFailRename = !folderRepository.rename(userId, folderId, name);
    if (isFailRename) {
      throw new BusinessException(FOLDER_RENAME_FAILED);
    }
  }

  @Transactional
  public void deleteFolder(Long userId, Long folderId) {
    FolderDetailResult folder = folderRepository.findByIdAndUserId(folderId, userId);
    if (folder.isDefault()) {
      throw new BusinessException(DEFAULT_FOLDER_CANNOT_BE_DELETE);
    }

    boolean isFailDelete = !folderRepository.delete(userId, folderId);
    if (isFailDelete) {
      throw new BusinessException(FOLDER_DELETE_FAILED);
    }
  }

  public List<RecordSummaryResult> getRecordList(Long userId, Long folderId) {
    folderRepository.verifyById(userId, folderId);
    return folderRepository.findAllRecordByIdAndUserId(userId, folderId);
  }

  @Transactional
  public QueryFolderMemoListResponse findAll(Long folderId, Long userId) {

    List<QueryPlaceMemoResponse> items =
        placeMemoRepository.findByFolderIdAndUserId(folderId, userId).stream()
            .map(QueryPlaceMemoResponse::from)
            .toList();

    List<RecordSummaryResponse> records =
        folderRepository.findAllRecordByIdAndUserId(userId, folderId).stream()
            .map(RecordSummaryResponse::from)
            .toList();

    return QueryFolderMemoListResponse.of(records, items);
  }
}
