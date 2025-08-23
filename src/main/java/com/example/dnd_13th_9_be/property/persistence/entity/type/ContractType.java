package com.example.dnd_13th_9_be.property.persistence.entity.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ContractType {
  MONTHLY_RENT("월세"),
  JEONSE("전세"),
  PURCHASE("매매");

  private final String name;
}
