package com.example.dnd_13th_9_be.placeMemo.persistence.converter;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.dnd_13th_9_be.common.support.converter.AbstractEntityConverter;
import com.example.dnd_13th_9_be.folder.persistence.entity.Folder;
import com.example.dnd_13th_9_be.placeMemo.application.model.PlaceMemoModel;
import com.example.dnd_13th_9_be.placeMemo.persistence.PlaceMemo;
import com.example.dnd_13th_9_be.placeMemo.persistence.PlaceMemoImage;
import com.example.dnd_13th_9_be.user.persistence.User;

@Component
public class PlaceMemoConverter implements AbstractEntityConverter<PlaceMemo, PlaceMemoModel> {

  @Override
  public PlaceMemoModel from(PlaceMemo placeMemo) {
    return PlaceMemoModel.builder()
        .id(placeMemo.getId())
        .title(placeMemo.getTitle())
        .placeTag(placeMemo.getPlaceTag())
        .description(placeMemo.getDescription())
        .folderId(placeMemo.getFolder().getId())
        .folderName(placeMemo.getFolder().getName())
        .address(placeMemo.getAddress())
        .latitude(placeMemo.getLatitude())
        .longitude(placeMemo.getLongitude())
        .imageUrls(
            placeMemo.getImages() == null
                ? List.of()
                : placeMemo.getImages().stream().map(PlaceMemoImage::getImageUrl).toList())
        .build();
  }

  @Override
  public PlaceMemo toEntity(PlaceMemoModel m) {
    return PlaceMemo.builder()
        .title(m.getTitle())
        .placeTag(m.getPlaceTag())
        .description(m.getDescription())
        .address(m.getAddress())
        .latitude(m.getLatitude())
        .longitude(m.getLongitude())
        .images(
            m.getImageUrls().stream()
                .map(url -> PlaceMemoImage.builder().imageUrl(url).build())
                .toList())
        .build();
  }

  public PlaceMemo toEntity(PlaceMemoModel placeMemoModel, User user, Folder folder) {
    return PlaceMemo.builder()
        .user(user)
        .folder(folder)
        .title(placeMemoModel.getTitle())
        .placeTag(placeMemoModel.getPlaceTag())
        .description(placeMemoModel.getDescription())
        .address(placeMemoModel.getAddress())
        .latitude(placeMemoModel.getLatitude())
        .longitude(placeMemoModel.getLongitude())
        .images(
            placeMemoModel.getImageUrls().stream()
                .map(url -> PlaceMemoImage.builder().imageUrl(url).build())
                .toList())
        .build();
  }
}
