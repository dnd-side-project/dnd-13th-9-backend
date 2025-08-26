package com.example.dnd_13th_9_be.property.persistence.dto;

import com.example.dnd_13th_9_be.property.persistence.entity.type.ContractType;
import com.example.dnd_13th_9_be.property.persistence.entity.type.FeelingType;

public record RecentPropertyResult(
    Long propertyId,
    String imageUrl,
    FeelingType feeling,
    String title,
    Integer depositBig,
    Integer depositSmall,
    Integer managementFee,
    ContractType contractType,
    String planName,
    String folderName) {}
