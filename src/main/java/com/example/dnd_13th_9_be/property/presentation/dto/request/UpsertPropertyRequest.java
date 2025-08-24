package com.example.dnd_13th_9_be.property.presentation.dto.request;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import com.example.dnd_13th_9_be.common.validator.EnumValid;
import com.example.dnd_13th_9_be.property.persistence.entity.type.ContractType;
import com.example.dnd_13th_9_be.property.persistence.entity.type.FeelingType;
import com.example.dnd_13th_9_be.property.persistence.entity.type.HouseType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;

public record UpsertPropertyRequest(
    List<Long> deletedImageIdList,
    @EnumValid(enumClass = FeelingType.class, message = "feeling 값은 GOOD, SOSO, BAD 중 하나여야 합니다")
        FeelingType feeling,
    @NotNull(message = "매물명은 필수입니다") @Length(min = 1, max = 10, message = "매물명은 1~10자 이하여야 합니다") String propertyName,
    @Length(min = 1, max = 80, message = "메모는 1~80자 이하여야 합니다") String memo,
    @Length(min = 1, max = 255, message = "참고 링크는 1~255자 이하여야 합니다") String referenceUrl,
    @NotNull(message = "지번 주소는 필수 입니다") @Length(min = 1, max = 50, message = "지번 주소는 1~50자 이하여야 합니다") String address,
    @Length(min = 1, max = 50, message = "상세 주소는 1~50자 이하여야 합니다") String detailAddress,
    @NotNull(message = "경도(x) 값은 필수 입니다 ")
        @DecimalMin(value = "-180.0", inclusive = true, message = "경도는 -180 이상이어야 합니다.")
        @DecimalMax(value = "180.0", inclusive = true, message = "경도는 180 이하여야 합니다.")
        Double longitude,
    @NotNull(message = "위도(y) 값은 필수 입니다")
        @DecimalMin(value = "-90.0", inclusive = true, message = "위도는 -90 이상이어야 합니다.")
        @DecimalMax(value = "90.0", inclusive = true, message = "위도는 90 이하여야 합니다.")
        Double latitude,
    @NotNull(message = "계약 형태는 필수 입니다") @EnumValid(
            enumClass = ContractType.class,
            message = "contractType 값은 MONTHLY_RENT, JEONSE, PURCHASE 중 하나여야 합니다")
        ContractType contractType,
    @EnumValid(
            enumClass = HouseType.class,
            message =
                "houseType 값은 OFFICETEL, ONEROOM, APARTMENT, VILLA, COLIVING, GOSIWON, BOARDING, ETC 중 하나여야 합니다")
        HouseType houseType,
    @NotNull(message = "억 단위는 필수 값입니다") @Min(value = 0, message = "억 단위는 필수 값입니다")
        @Max(value = 1000, message = "억 단위는 1000억 이상이 될 수 없습니다")
        Integer depositBig,
    @NotNull(message = "만원 단위는 필수 값입니다") @Min(value = 0, message = "만원 단위는 필수 값입니다")
        @Max(value = 9999, message = "만원 단위는 9999만원 이상이 될 수 없습니다")
        Integer depositSmall,
    @Min(value = 0, message = "관리비는 최소 0만원 부터 가능합니다")
        @Max(value = 10000, message = "관리비는 1억 미만이어야 합니다")
        Integer managementFee,
    @Length(min = 1, max = 50, message = "입주 가능 시기는 1~50자 이하여야 합니다") String moveInInfo,
    @Valid List<PropertyCategoryMemoRequest> categoryMemoList,
    @NotNull(message = "폴더 인덱스 값은 필수 입니다") @Min(value = 1, message = "유효한 폴더 인덱스 값이 아닙니다")
        Long folderId) {

  @JsonIgnore
  @AssertTrue(message = "계약 형태가 월세일 때만 managementFee 값이 허용됩니다")
  public boolean isValidManagementFee() {
    if (contractType == ContractType.MONTHLY_RENT) {
      return true;
    }

    return managementFee == null;
  }

  @JsonIgnore
  public List<PropertyCategoryMemoRequest> getCategoryMemo() {
    if (this.categoryMemoList == null) return new ArrayList<>();
    return this.categoryMemoList.stream()
        .filter(Objects::nonNull)
        .filter(m -> m.categoryId() != 0L)
        .toList();
  }

  @JsonIgnore
  public String getRequiredCheckMemo() {
    return Optional.ofNullable(this.categoryMemoList())
        .flatMap(
            list ->
                list.stream()
                    .filter(v -> Objects.equals(v.categoryId(), 0L))
                    .map(PropertyCategoryMemoRequest::memo)
                    .findFirst())
        .orElse(null);
  }
}
