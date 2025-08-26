package com.example.dnd_13th_9_be.checklist.presentation.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public record Section(Long categoryId, String categoryName, String memo, List<SectionItem> items) {}
