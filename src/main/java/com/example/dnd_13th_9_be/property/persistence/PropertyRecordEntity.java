package com.example.dnd_13th_9_be.property.persistence;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
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
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.example.dnd_13th_9_be.folder.persistence.FolderEntity;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "property_record")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PropertyRecordEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "folder_id", nullable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  private FolderEntity folder;

  @Comment("매물명")
  @Column(nullable = false, length = 10)
  private String title;

  @Comment("전반적인 느낌")
  private Integer rating;

  @Comment("메모")
  @Column(length = 2000)
  private String memo;

  @Comment("참고 링크")
  @Column(name = "reference_url")
  private String referenceUrl;

  @Comment("주소")
  @Column(nullable = false)
  private String address;

  @Comment("위도")
  @Column(nullable = false)
  private Double latitude;

  @Comment("경도")
  @Column(nullable = false)
  private Double longitude;

  @Comment("계약 형태")
  @Enumerated(EnumType.STRING)
  @Column(name = "contract_type", nullable = false)
  private ContractType contractType;

  @Comment("집 유형")
  @Enumerated(EnumType.STRING)
  @Column(name = "house_type")
  private HouseType houseType;

  @Comment("보증금/전세금/매매가 (만원)")
  @Column(name = "price", nullable = false)
  private BigDecimal price;

  @Comment("집 유형이 월세인 경우 월세 값")
  @Column(name = "monthly_rent_price")
  private BigDecimal monthlyRentPrice;

  @Comment("관리비")
  @Column(name = "management_fee", nullable = false)
  private BigDecimal managementFee;

  @Comment("입주 가능 시기 (예: 9월 초, 즉시입주, 협의 가능)")
  @Column(name = "move_in_info")
  private String moveInInfo;

  @CreationTimestamp
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @OrderBy("imageOrder ASC")
  @OneToMany(
      mappedBy = "propertyRecord",
      cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  private List<PropertyImageEntity> images;

  @OneToMany(
      mappedBy = "propertyRecord",
      cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  private Set<PropertyTransportEntity> transports;
}
