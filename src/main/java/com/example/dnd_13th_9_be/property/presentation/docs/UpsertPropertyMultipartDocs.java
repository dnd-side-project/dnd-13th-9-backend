package com.example.dnd_13th_9_be.property.presentation.docs;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.dnd_13th_9_be.property.presentation.dto.request.UpsertPropertyRequest;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "CreatePropertyMultipart", description = "멀티파트 업로드 폼")
public class UpsertPropertyMultipartDocs {

  @Schema(description = "요청 본문(JSON)", required = true)
  public UpsertPropertyRequest data;

  @Schema(description = "이미지 파일들(여러 장 가능, 같은 키 'image'로 반복 첨부)")
  public List<MultipartFile> image;
}
