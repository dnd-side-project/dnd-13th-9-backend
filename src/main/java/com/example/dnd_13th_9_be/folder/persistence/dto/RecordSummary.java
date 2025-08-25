package com.example.dnd_13th_9_be.folder.persistence.dto;

import java.sql.Timestamp;
import java.util.List;

import com.example.dnd_13th_9_be.folder.persistence.entity.RecordType;
import com.example.dnd_13th_9_be.property.persistence.entity.type.ContractType;
import com.example.dnd_13th_9_be.property.persistence.entity.type.FeelingType;

public record RecordSummary(
    List<RecordImageSummary> images,
    Long id,
    RecordType recordType,
    FeelingType feeling,
    String title,
    ContractType contractType,
    Integer depositBig,
    Integer depositSmall,
    Integer managementFee,
    String memo,
    String locationTag,
    Double latitude,
    Double longitude,
    Timestamp createdAt) {

  public record RecordImageSummary(String url, Integer order) {}
}
