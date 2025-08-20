package com.example.dnd_13th_9_be.checklist.application.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import com.example.dnd_13th_9_be.common.support.AbstractModel;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@ToString
public class ChecklistItemModel implements AbstractModel {
  private Long id;
  private Long categoryId;
  private String question;
  private String description;
  private Integer sortOrder;
}
