package com.example.dnd_13th_9_be.property.persistence;

import java.util.List;
import jakarta.persistence.EntityManager;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import com.example.dnd_13th_9_be.folder.persistence.entity.Folder;
import com.example.dnd_13th_9_be.folder.persistence.entity.QFolder;
import com.example.dnd_13th_9_be.global.error.BusinessException;
import com.example.dnd_13th_9_be.plan.persistence.entity.QPlan;
import com.example.dnd_13th_9_be.property.application.model.PropertyModel;
import com.example.dnd_13th_9_be.property.application.repository.PropertyRepository;
import com.example.dnd_13th_9_be.property.persistence.dto.PropertyResult;
import com.example.dnd_13th_9_be.property.persistence.dto.RecentPropertyResult;
import com.example.dnd_13th_9_be.property.persistence.entity.Property;
import com.example.dnd_13th_9_be.property.persistence.entity.QProperty;
import com.example.dnd_13th_9_be.property.persistence.entity.QPropertyImage;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import static com.example.dnd_13th_9_be.global.error.ErrorCode.NOT_FOUND_PROPERTY;

@Component
@RequiredArgsConstructor
public class PropertyRepositoryImpl implements PropertyRepository {
  private final EntityManager em;
  private final JPAQueryFactory query;
  private final JpaPropertyRepository jpaPropertyRepository;

  @Override
  public void verifyExistsById(Long userId, Long propertyId) {
    var property = QProperty.property;
    Long result =
        query
            .select(property.id)
            .from(property)
            .where(property.id.eq(propertyId).and(property.folder.user.id.eq(userId)))
            .fetchFirst();
    if (result == null) {
      throw new BusinessException(NOT_FOUND_PROPERTY);
    }
  }

  @Override
  public void delete(Long userId, Long propertyId) {
    var property = QProperty.property;

    long affected =
        query
            .delete(property)
            .where(property.id.eq(propertyId).and(property.folder.user.id.eq(userId)))
            .execute();
    if (affected == 0) {
      throw new BusinessException(NOT_FOUND_PROPERTY);
    }
  }

  @Override
  public PropertyResult findById(Long propertyId) {
    Property property =
        jpaPropertyRepository
            .findByIdWithFolder(propertyId)
            .orElseThrow(() -> new BusinessException(NOT_FOUND_PROPERTY));
    return PropertyResult.from(property);
  }

  @Override
  public PropertyResult save(PropertyModel dto) {
    Folder folder = em.getReference(Folder.class, dto.folderId());
    Property property =
        Property.builder()
            .folder(folder)
            .title(dto.title())
            .feeling(dto.feeling())
            .memo(dto.memo())
            .referenceUrl(dto.referenceUrl())
            .address(dto.address())
            .detailAddress(dto.detailAddress())
            .latitude(dto.latitude())
            .longitude(dto.longitude())
            .contractType(dto.contractType())
            .houseType(dto.houseType())
            .depositBig(dto.depositBig())
            .depositSmall(dto.depositSmall())
            .managementFee(dto.managementFee())
            .moveInInfo(dto.moveInInfo())
            .requiredCheckMemo(dto.requiredCheckMemo())
            .build();
    Property savedProperty = jpaPropertyRepository.save(property);
    return PropertyResult.from(savedProperty);
  }

  @Override
  public void update(Long userId, Long propertyId, PropertyModel dto) {
    var property = QProperty.property;
    Property savedProperty =
        query
            .selectFrom(property)
            .where(property.id.eq(propertyId).and(property.folder.user.id.eq(userId)))
            .fetchFirst();
    if (savedProperty == null) {
      throw new BusinessException(NOT_FOUND_PROPERTY);
    }
    savedProperty.update(dto);
    jpaPropertyRepository.save(savedProperty);
  }

  public List<RecentPropertyResult> findTopByUserId(Long userId) {
    var property = QProperty.property;
    var propertyImage = QPropertyImage.propertyImage;
    var plan = QPlan.plan;
    var folder = QFolder.folder;

    return query
        .from(property)
        .join(property.folder, folder)
        .leftJoin(folder.plan, plan)
        .leftJoin(propertyImage)
        .on(propertyImage.property.eq(property).and(propertyImage.imageOrder.eq(1)))
        .where(folder.user.id.eq(userId))
        .orderBy(property.createdAt.desc(), property.id.desc())
        .limit(10)
        .select(
            Projections.constructor(
                RecentPropertyResult.class,
                property.id,
                propertyImage.imageUrl,
                property.feeling,
                property.title,
                property.depositBig,
                property.depositSmall,
                property.managementFee,
                property.contractType,
                plan.name,
                folder.name))
        .fetch();
  }
}
