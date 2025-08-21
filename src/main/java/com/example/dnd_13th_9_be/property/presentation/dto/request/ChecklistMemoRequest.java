package com.example.dnd_13th_9_be.property.presentation.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record ChecklistMemoRequest(
    @NotNull(message = "카테고리 인덱스 값은 필수 입니다")
    @Min(value = 1, message = "유효한 카테고리 값이 아닙니다")
    Long categoryId,

    @NotNull(message = "카테고리별 메모 값은 필수 입니다")
    @Length(min = 1, max = 200, message = "카테고리별 메모는 1~200자 이하여야 합니다")
    String memo
) {

}
