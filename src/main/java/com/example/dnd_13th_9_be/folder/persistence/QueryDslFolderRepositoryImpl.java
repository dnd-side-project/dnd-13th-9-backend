package com.example.dnd_13th_9_be.folder.persistence;

import java.util.List;
import java.util.Optional;

import com.example.dnd_13th_9_be.placeMemo.persistence.QPlaceMemo;
import com.querydsl.jpa.JPQLQuery;
import lombok.RequiredArgsConstructor;

import com.example.dnd_13th_9_be.folder.persistence.dto.FolderSummary;
import com.example.dnd_13th_9_be.folder.persistence.dto.RecordSummary;
import com.example.dnd_13th_9_be.folder.persistence.entity.QFolder;
import com.example.dnd_13th_9_be.folder.persistence.entity.RecordType;
import com.example.dnd_13th_9_be.location.persistence.QLocationRecordEntity;
import com.example.dnd_13th_9_be.property.persistence.entity.QProperty;
import com.example.dnd_13th_9_be.property.persistence.entity.QPropertyImage;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;

@RequiredArgsConstructor
public class QueryDslFolderRepositoryImpl implements QueryDslFolderRepository {
  private final JPAQueryFactory query;

  @Override
  public List<FolderSummary> findSummariesByPlanId(Long userId, Long planId) {
    var folder = QFolder.folder;
    var property = QProperty.property;

    QPlaceMemo placeMemo = QPlaceMemo.placeMemo;

    JPQLQuery<Long> placeMemoCnt = JPAExpressions.select(placeMemo.id.count())
            .from(placeMemo)
            .where(placeMemo.folder.id.eq(folder.id));


    var propertyCnt =
        JPAExpressions.select(property.id.count())
            .from(property)
            .where(property.folder.id.eq(folder.id));

    return query
        .select(
            Projections.constructor(
                FolderSummary.class,
                folder.id,
                folder.name,
                folder.createdAt,
                placeMemoCnt,
                propertyCnt,
                folder.isDefault))
        .from(folder)
        .where(folder.plan.id.eq(planId).and(folder.user.id.eq(userId)))
        .orderBy(folder.createdAt.desc())
        .fetch();
  }

  @Override
  public boolean rename(Long userId, Long folderId, String newName) {
    var folder = QFolder.folder;
    long affected =
        query
            .update(folder)
            .set(folder.name, newName)
            .where(folder.id.eq(folderId).and(folder.user.id.eq(userId)))
            .execute();
    return affected == 1L;
  }

  @Override
  public boolean deleteByIdIfExists(Long userId, Long folderId) {
    var folder = QFolder.folder;
    var affected =
        query.delete(folder).where(folder.id.eq(folderId).and(folder.user.id.eq(userId))).execute();
    return affected == 1L;
  }

  @Override
  public Long countByPlanId(Long planId) {
    var folder = QFolder.folder;
    return query.select(folder.id.count()).from(folder).where(folder.plan.id.eq(planId)).fetchOne();
  }

  @Override
  public Long countFolderRecord(Long folderId) {
    var property = QProperty.property;
    var location = QLocationRecordEntity.locationRecordEntity;
    long propertyCnt =
        Optional.ofNullable(
                query
                    .select(property.id.count())
                    .from(property)
                    .where(property.folder.id.eq(folderId))
                    .fetchOne())
            .orElse(0L);

    long locationCnt =
        Optional.ofNullable(
                query
                    .select(location.id.count())
                    .from(location)
                    .where(location.folder.id.eq(folderId))
                    .fetchOne())
            .orElse(0L);

    return propertyCnt + locationCnt;
  }

  @Override
  public List<RecordSummary> findAllRecordByIdAndUserId(Long userId, Long folderId) {
    var property = QProperty.property;
    var propertyImage = QPropertyImage.propertyImage;

    return query
        .from(property)
        .leftJoin(propertyImage)
        .on(propertyImage.property.eq(property))
        .where(property.folder.id.eq(folderId).and(property.folder.user.id.eq(userId)))
        .orderBy(
            property.createdAt.desc(),
            property.id.desc(),
            propertyImage.imageOrder.asc(),
            propertyImage.id.asc())
        .transform(
            groupBy(property.id)
                .list(
                    Projections.constructor(
                        RecordSummary.class,
                        list(
                            Projections.constructor(
                                RecordSummary.RecordImageSummary.class,
                                propertyImage.imageUrl,
                                propertyImage.imageOrder)),
                        property.id,
                        Expressions.constant(RecordType.PROPERTY),
                        property.feeling,
                        property.title,
                        property.contractType,
                        property.depositBig,
                        property.depositSmall,
                        property.managementFee,
                        property.memo,
                        Expressions.nullExpression(String.class),
                        property.latitude,
                        property.longitude,
                        property.createdAt)));
  }
}
