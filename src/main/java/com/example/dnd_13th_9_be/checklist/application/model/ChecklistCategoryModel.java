package com.example.dnd_13th_9_be.checklist.application.model;

import java.util.List;

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
public class ChecklistCategoryModel implements AbstractModel {
  private Long id;
  private String name;
  private Integer sortOrder;
  private List<ChecklistItemModel> items;
}
