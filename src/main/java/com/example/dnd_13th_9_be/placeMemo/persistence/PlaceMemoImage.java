package com.example.dnd_13th_9_be.placeMemo.persistence;

import com.example.dnd_13th_9_be.common.persistence.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "place_memo_image")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlaceMemoImage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_memo_id", nullable = false)
    PlaceMemo placeMemo;

    @Column(nullable = false)
    private String imageUrl;

    @Column(name = "order_index", nullable = false)
    private Integer orderIndex;



}
