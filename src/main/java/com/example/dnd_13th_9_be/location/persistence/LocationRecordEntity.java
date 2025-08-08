package com.example.dnd_13th_9_be.location.persistence;

import java.time.LocalDateTime;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.example.dnd_13th_9_be.folder.persistence.FolderEntity;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "location_record")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LocationRecordEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "folder_id", nullable = false)
  private FolderEntity folder;

  @Comment("장소 메모 제목")
  private String title;

  @Comment("주소")
  @Column(nullable = false)
  private String address;

  @Comment("위도")
  @Column(nullable = false)
  private Double latitude;

  @Comment("경도")
  @Column(nullable = false)
  private Double longitude;

  @Comment("메모 유형")
  @Enumerated(EnumType.STRING)
  @Column(name = "record_type")
  private LocationRecordType recordType;

  @Comment("메모")
  @Column(length = 2000)
  private String memo;

  @CreationTimestamp
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @OneToMany(
      mappedBy = "locationRecord",
      cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  private List<LocationImageEntity> images;
}
