package com.example.dnd_13th_9_be.plan.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RenamePlanRequest(@NotBlank @Size(max = 10) String name) {}
