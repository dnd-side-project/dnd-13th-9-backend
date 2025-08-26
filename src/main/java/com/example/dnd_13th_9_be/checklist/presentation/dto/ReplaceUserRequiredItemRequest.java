package com.example.dnd_13th_9_be.checklist.presentation.dto;

import java.util.List;
import jakarta.validation.constraints.NotNull;

import org.hibernate.validator.constraints.UniqueElements;

public record ReplaceUserRequiredItemRequest(
    @NotNull(message = "itemIdList는 필수 값입니다") @UniqueElements(message = "중복되는 itemId가 있습니다")
        List<Long> itemIdList) {}
