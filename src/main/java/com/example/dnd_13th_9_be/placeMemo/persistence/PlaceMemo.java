package com.example.dnd_13th_9_be.placeMemo.persistence;


import com.example.dnd_13th_9_be.common.persistence.BaseEntity;
import com.example.dnd_13th_9_be.folder.persistence.entity.Folder;
import com.example.dnd_13th_9_be.user.persistence.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
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

    @OneToMany(mappedBy = "placeMemo", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("orderIndex ASC")
    private List<PlaceMemoImage> images = new ArrayList<>();

    public void addImage(PlaceMemoImage image){
        images.add(image);
        image.setPlaceMemo(this);
    }

    public void removeImage(PlaceMemoImage image){
        images.remove(image);
        image.setPlaceMemo(null);
    }
}
