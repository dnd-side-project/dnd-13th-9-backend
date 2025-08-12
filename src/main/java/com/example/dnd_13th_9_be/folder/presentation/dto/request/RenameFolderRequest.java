package com.example.dnd_13th_9_be.folder.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RenameFolderRequest(
    @NotBlank @Size(max = 10) String name
) {

}
