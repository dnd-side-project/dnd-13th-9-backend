package com.example.dnd_13th_9_be.property.application;

import com.example.dnd_13th_9_be.checklist.application.repository.UserRequiredItemRepository;
import com.example.dnd_13th_9_be.common.utils.S3Manager;
import com.example.dnd_13th_9_be.global.error.S3ImageException;
import com.example.dnd_13th_9_be.property.application.model.PropertyImageModel;
import com.example.dnd_13th_9_be.property.application.model.PropertyRecordModel;
import com.example.dnd_13th_9_be.property.application.model.PropertyRequiredCheckModel;
import com.example.dnd_13th_9_be.property.application.repository.PropertyRecordRepository;
import com.example.dnd_13th_9_be.property.presentation.dto.request.PropertyRecordRequest;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class PropertyRecordService {

    private final S3Manager s3Manager;
    private final PropertyRecordRepository propertyRecordRepository;
    private final UserRequiredItemRepository userRequiredItemRepository;

    @Transactional(rollbackFor = {S3ImageException.class})
    public PropertyRecordModel createPropertyRecord(Long userId, List<MultipartFile> files, PropertyRecordRequest request) {

        AtomicInteger imageOrder = new AtomicInteger(1);
        List<PropertyImageModel> images = Optional.ofNullable(files)
            .orElseGet(Collections::emptyList)
            .stream().map(
            file -> PropertyImageModel.builder()
                .imageUrl(s3Manager.upload(file))
                .sort(imageOrder.getAndIncrement())
                .build()
        ).toList();

        List<PropertyRequiredCheckModel> requiredCheckModelList = userRequiredItemRepository.findAllByUserId(userId)
            .stream().map(
                m -> PropertyRequiredCheckModel.builder()
                    .checklistId(m.getItemId())
                    .build()
            ).toList();

        return propertyRecordRepository.save(PropertyRecordModel.from(images, request, requiredCheckModelList));
    }

    @Transactional
    public void deleteProperty(Long userId, Long propertyId) {
        propertyRecordRepository.delete(userId, propertyId);
    }
}
