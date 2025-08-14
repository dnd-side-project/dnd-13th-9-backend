package com.example.dnd_13th_9_be.folder.presentation.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateFolderRequest(
    @NotNull @Min(1) Long planId, @NotBlank @Size(max = 10) String name) {}
