package com.example.dnd_13th_9_be.placeMemo.application.model.converter;

import com.example.dnd_13th_9_be.placeMemo.application.dto.CreatePlaceMemoRequest;
import com.example.dnd_13th_9_be.placeMemo.application.model.PlaceMemoModel;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PlaceMemoRequestConverter {

    public PlaceMemoModel from(CreatePlaceMemoRequest request, List<String> imageUrls){
        return PlaceMemoModel.builder()
                .title(request.title())
                .description(request.description())
                .placeTag(request.placeTag())
                .address(request.address())
                .latitude(request.latitude())
                .longitude(request.longitude())
                .folderId(request.folderId())
                .imageUrls(imageUrls == null ? List.of() : imageUrls)
                .build();
    }
}
