package com.example.dnd_13th_9_be.folder.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RecordType {
    PROPERTY("매물 메모"),
    LOCATION("장소 메모");

    private final String name;
}
