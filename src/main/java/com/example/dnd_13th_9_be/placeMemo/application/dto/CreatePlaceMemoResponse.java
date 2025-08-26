package com.example.dnd_13th_9_be.placeMemo.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class CreatePlaceMemoResponse {
  private Long placeMemoId;
}
