package com.example.dnd_13th_9_be.placeMemo.persistence;

import com.example.dnd_13th_9_be.common.persistence.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "place_memo_image")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlaceMemoImage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String imageUrl;

    @Builder
    public PlaceMemoImage(String imageUrl){
        this.imageUrl = imageUrl;
    }


}
