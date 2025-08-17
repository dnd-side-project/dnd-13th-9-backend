package com.example.dnd_13th_9_be.checklist.presentation.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public record SectionItem(Long id, String question, String description) {}
