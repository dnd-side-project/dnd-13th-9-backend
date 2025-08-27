package com.example.dnd_13th_9_be.property.persistence.entity;

import java.util.List;
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
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.example.dnd_13th_9_be.common.persistence.BaseEntity;
import com.example.dnd_13th_9_be.folder.persistence.entity.Folder;
import com.example.dnd_13th_9_be.property.application.model.PropertyModel;
import com.example.dnd_13th_9_be.property.persistence.entity.type.ContractType;
import com.example.dnd_13th_9_be.property.persistence.entity.type.FeelingType;
import com.example.dnd_13th_9_be.property.persistence.entity.type.HouseType;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "property")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Property extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "folder_id", nullable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Folder folder;

  @Comment("매물명")
  @Column(nullable = false, length = 10)
  private String title;

  @Comment("전반적인 느낌")
  @Enumerated(EnumType.STRING)
  @Column(length = 5)
  private FeelingType feeling;

  @Comment("메모")
  @Column(length = 80)
  private String memo;

  @Comment("참고 링크")
  @Column(name = "reference_url")
  private String referenceUrl;

  @Comment("주소")
  @Column(nullable = false, length = 50)
  private String address;

  @Comment("상세 주소")
  @Column(length = 50)
  private String detailAddress;

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

  @Comment("보증금/전세금/매매가 억 단위")
  @Column(name = "deposit_big", nullable = false)
  private Integer depositBig;

  @Comment("보증금/전세금/매매가 만원 단위")
  @Column(name = "deposit_small")
  private Integer depositSmall;

  @Comment("관리비")
  @Column(name = "management_fee")
  private Integer managementFee;

  @Comment("월세")
  @Column(name = "monthly_fee")
  private Integer monthlyFee;

  @Comment("입주 가능 시기 (예: 9월 초, 즉시입주, 협의 가능)")
  @Column(name = "move_in_info", length = 50)
  private String moveInInfo;

  @Comment("필수 확인 메모")
  @Column(name = "required_check_memo", length = 200)
  private String requiredCheckMemo;

  @Builder
  private Property(
      Folder folder,
      String title,
      FeelingType feeling,
      String memo,
      String referenceUrl,
      String address,
      String detailAddress,
      Double latitude,
      Double longitude,
      ContractType contractType,
      HouseType houseType,
      Integer depositBig,
      Integer depositSmall,
      Integer managementFee,
      Integer monthlyFee,
      String moveInInfo,
      String requiredCheckMemo,
      List<PropertyImage> images) {
    this.folder = folder;
    this.title = title;
    this.feeling = feeling;
    this.memo = memo;
    this.referenceUrl = referenceUrl;
    this.address = address;
    this.detailAddress = detailAddress;
    this.latitude = latitude;
    this.longitude = longitude;
    this.contractType = contractType;
    this.houseType = houseType;
    this.depositBig = depositBig;
    this.depositSmall = depositSmall;
    this.managementFee = managementFee;
    this.monthlyFee = monthlyFee;
    this.moveInInfo = moveInInfo;
    this.requiredCheckMemo = requiredCheckMemo;
  }

  public void update(PropertyModel dto) {
    this.title = dto.title();
    this.feeling = dto.feeling();
    this.memo = dto.memo();
    this.referenceUrl = dto.referenceUrl();
    this.address = dto.address();
    this.detailAddress = dto.detailAddress();
    this.latitude = dto.latitude();
    this.longitude = dto.longitude();
    this.contractType = dto.contractType();
    this.houseType = dto.houseType();
    this.depositBig = dto.depositBig();
    this.depositSmall = dto.depositSmall();
    this.managementFee = dto.managementFee();
    this.monthlyFee = dto.monthlyFee();
    this.moveInInfo = dto.moveInInfo();
    this.requiredCheckMemo = dto.requiredCheckMemo();
  }
}
