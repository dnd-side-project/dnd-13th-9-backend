package com.example.dnd_13th_9_be.placeMemo.application.model;

import java.util.List;

import lombok.*;

import com.example.dnd_13th_9_be.common.support.AbstractModel;
import com.example.dnd_13th_9_be.placeMemo.persistence.PlaceTag;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class PlaceMemoModel implements AbstractModel {

  private Long id;
  private String title;
  private PlaceTag placeTag;
  private String description;
  private String address;
  private String latitude;
  private String longitude;
  private Long folderId;
  private String folderName;
  @Builder.Default private List<String> imageUrls = List.of();
}
