package com.example.dnd_13th_9_be.placeMemo.application.dto;

import com.example.dnd_13th_9_be.placeMemo.persistence.PlaceTag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public record QueryPlaceMemoRequest(
    @NotBlank(message = "장소명은 필수값입니다.")
    @Size(max = 20, message = "장소명은 20자 이내로 입력해주세요.")
    String title,
    @NotNull(message = "장소 태그는 필수값입니다.")
    PlaceTag placeTag,
    @Size(max = 100, message = "메모는 100자 이재로 입력해주세요.") String description,
    @NotBlank(message = "주소는 필수값입니다.")
    @Size(max = 200, message = "주소는 200자 이내로 입력해주세요.")
    String address,
    @NotBlank(message = "위도는 필수값입니다.") String latitude,
    @NotBlank(message = "경도는 필수값입니다.") String longitude,
    @NotNull(message = "폴더는 필수값입니다.") Long folderId,
    @Size(max = 5, message = "이미지는 최대 5개까지 첨부할 수 있습니다.")
    List<MultipartFile> images
){

}

