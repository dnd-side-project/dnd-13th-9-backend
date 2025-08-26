package com.example.dnd_13th_9_be.placeMemo.persistence;


import com.example.dnd_13th_9_be.common.persistence.BaseEntity;
import com.example.dnd_13th_9_be.folder.persistence.entity.Folder;
import com.example.dnd_13th_9_be.placeMemo.application.model.PlaceMemoModel;
import com.example.dnd_13th_9_be.user.persistence.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "place_memo")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlaceMemo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "folder_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Folder folder;


    @Comment("장소 이름")
    @Column(name = "place_memo_title", nullable = false, length = 20)
    private String title;


    @Enumerated(EnumType.STRING)
    @Column(name = "place_tag", nullable = false)
    private PlaceTag placeTag;

    @Comment("장소 메모")
    @Column(name = "place_memo_description", nullable = true, length = 100)
    private String description;

    @Comment("주소")
    @Column(name = "address", nullable = false, length = 200)
    private String address;

    @Comment("위도")
    @Column(name = "latitude", nullable = false)
    private String latitude;

    @Comment("경도")
    @Column(name = "longitude", nullable = false)
    private String longitude;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "place_memo_id") // FK를 PlaceMemoImage 테이블에 생성
    private List<PlaceMemoImage> images = new ArrayList<>();

    @Builder
    public PlaceMemo(User user, Folder folder, String title, PlaceTag placeTag, String description,
                     String address, String latitude, String longitude, List<PlaceMemoImage> images){
        this.user = user;
        this.folder = folder;
        this.title = title;
        this.placeTag = placeTag;
        this.description = description;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        if(images != null){
            this.images = images;
        }
    }

    public void updateFrom(PlaceMemoModel model) {
        this.title = model.getTitle();
        this.description = model.getDescription();
        this.address = model.getAddress();
        this.latitude = model.getLatitude();
        this.longitude = model.getLongitude();
        this.placeTag = model.getPlaceTag();

        this.images.clear();
        if (model.getImageUrls() != null) {
            model.getImageUrls().forEach(url ->
                    this.images.add(PlaceMemoImage.builder().imageUrl(url).build())
            );
        }
    }
}
