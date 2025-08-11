package com.example.dnd_13th_9_be.common.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;



@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})
@SuperBuilder(toBuilder = true)
public class BaseEntity {

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Timestamp createdDate;

    @LastModifiedDate
    @Column(nullable = false)
    private Timestamp updatedDate;

}
