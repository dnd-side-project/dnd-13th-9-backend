package com.example.dnd_13th_9_be.placeMemo.persistence;

import com.example.dnd_13th_9_be.folder.application.repository.FolderRepository;
import com.example.dnd_13th_9_be.folder.persistence.JpaFolderRepository;
import com.example.dnd_13th_9_be.folder.persistence.entity.Folder;
import com.example.dnd_13th_9_be.global.error.BusinessException;
import com.example.dnd_13th_9_be.global.error.ErrorCode;
import com.example.dnd_13th_9_be.global.error.UserNotFoundException;
import com.example.dnd_13th_9_be.placeMemo.application.model.PlaceMemoModel;
import com.example.dnd_13th_9_be.placeMemo.persistence.converter.PlaceMemoConverter;
import com.example.dnd_13th_9_be.placeMemo.application.repository.PlaceMemoRepository;
import com.example.dnd_13th_9_be.user.persistence.JpaUserRepository;
import com.example.dnd_13th_9_be.user.persistence.User;
import com.example.dnd_13th_9_be.user.persistence.UserRepositoryImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class PlaceMemoRepositoryImpl implements PlaceMemoRepository {
    private final JpaPlaceMemoRepository jpaPlaceMemoRepository;
    private final PlaceMemoConverter placeMemoConverter;
    private final UserRepositoryImpl userRepository;
    private final FolderRepository folderRepository;
    private final JpaFolderRepository jpaFolderRepository;
    private final JpaUserRepository jpaUserRepository;

    @Override
    public PlaceMemoModel save(Long userId, PlaceMemoModel placeMemoModel) {
        User user = jpaUserRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        //TODO : folder model 추가, 리팩토링
        Folder folder = jpaFolderRepository.findById(placeMemoModel.getFolderId()).orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_FOLDER));

        return placeMemoConverter.from(jpaPlaceMemoRepository.save(placeMemoConverter.toEntity(placeMemoModel, user, folder)));
    }

    @Override
    public Optional<PlaceMemoModel> findByIdAndUserId(Long placeMemoId, Long userId){
        return jpaPlaceMemoRepository.findByIdAndUserId(placeMemoId, userId).map(placeMemoConverter::from);
    }

    @Override
    public void deleteById(Long placeMemoId, Long userId){
        jpaPlaceMemoRepository.findByIdAndUserId(placeMemoId, userId)
                .ifPresent(jpaPlaceMemoRepository::delete);
    }

    @Override
    public List<PlaceMemoModel> findByFolderIdAndUserId(Long folderId, Long userId) {
        return jpaPlaceMemoRepository.findByFolderIdAndUserIdOrderByIdDesc(folderId, userId)
                .stream()
                .map(placeMemoConverter::from)
                .toList();
    }

    @Override
    public void update(Long userId, Long placeMemoId, PlaceMemoModel model) {
        PlaceMemo entity = jpaPlaceMemoRepository.findByIdAndUserId(placeMemoId, userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_PLACE_MEMO));

        entity.updateFrom(model);
        jpaPlaceMemoRepository.save(entity);

    }
}
