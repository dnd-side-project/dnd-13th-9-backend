package com.example.dnd_13th_9_be.placeMemo.application.model;

import com.example.dnd_13th_9_be.common.support.AbstractModel;
import com.example.dnd_13th_9_be.placeMemo.persistence.PlaceTag;
import lombok.*;

import java.util.List;

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
    @Builder.Default
    private List<String> imageUrls = List.of();


    public PlaceMemoModel(PlaceMemoModel model){
        this.id = model.getId();
        this.title = model.getTitle();
        this.placeTag = model.getPlaceTag();
        this.description = model.getDescription();
        this.address = model.getAddress();
        this.latitude = model.getLatitude();
        this.longitude = model.getLongitude();
        this.folderId = model.getFolderId();
        this.imageUrls = model.getImageUrls();
    }

}
