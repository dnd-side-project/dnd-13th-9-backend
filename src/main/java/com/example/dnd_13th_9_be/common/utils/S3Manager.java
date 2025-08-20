package com.example.dnd_13th_9_be.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

import com.example.dnd_13th_9_be.global.error.ErrorCode;
import com.example.dnd_13th_9_be.global.error.S3ImageException;
import com.google.common.base.Strings;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

@Component
@RequiredArgsConstructor
public class S3Manager {

  private final S3Client s3Client;

  @Value("${spring.cloud.aws.s3.bucket}")
  private String bucket;

  public String upload(MultipartFile file) {
    String fileName = createUniqueFileName(file.getOriginalFilename());

    try (InputStream inputStream = file.getInputStream()) {
      PutObjectRequest putObjectRequest =
          PutObjectRequest.builder()
              .bucket(bucket)
              .key(fileName)
              .contentType(file.getContentType())
              .contentLength(file.getSize())
              .build();

      s3Client.putObject(
          putObjectRequest, RequestBody.fromInputStream(inputStream, file.getSize()));
    } catch (IOException e) {
      throw new S3ImageException(ErrorCode.INVALID_FILE_URL, "파일 업로드 실패");
    }

    return generateFileUrl(fileName);
  }

  public String getImageUrl(String fileKey) {
    return generateFileUrl(fileKey);
  }

  public String update(String oldFileUrl, MultipartFile newFile) {
    String newFileUrl = upload(newFile);
    delete(oldFileUrl);
    return newFileUrl;
  }

  public void delete(String fileUrl) {
    if (!StringUtils.hasText(fileUrl)) {
      throw new S3ImageException(ErrorCode.FILE_NOT_FOUND);
    }
    String fileKey = extractFileKey(fileUrl);

    DeleteObjectRequest deleteRequest =
        DeleteObjectRequest.builder().bucket(bucket).key(fileKey).build();

    s3Client.deleteObject(deleteRequest);
  }

  public void deleteMultiple(List<String> fileUrls) {
    if (fileUrls == null || fileUrls.isEmpty()) {
      throw new S3ImageException(ErrorCode.FILE_NOT_FOUND);
    }

    List<ObjectIdentifier> objectsToDelete =
        fileUrls.stream()
            .filter(StringUtils::hasText)
            .map(this::extractFileKey)
            .map(key -> ObjectIdentifier.builder().key(key).build())
            .toList();

    if (objectsToDelete.isEmpty()) {
      return;
    }

    DeleteObjectsRequest deleteObjectsRequest =
        DeleteObjectsRequest.builder()
            .bucket(bucket)
            .delete(Delete.builder().objects(objectsToDelete).build())
            .build();

    s3Client.deleteObjects(deleteObjectsRequest);
  }

  private String createUniqueFileName(String originalFileName) {
    String extension = "";
    if (originalFileName != null && originalFileName.contains(".")) {
      extension = originalFileName.substring(originalFileName.lastIndexOf("."));
    }
    return "images/" + UUID.randomUUID() + extension;
  }

  private String extractFileKey(String fileUrl) {
    if (Strings.isNullOrEmpty(fileUrl)) {
      throw new S3ImageException(ErrorCode.INVALID_FILE_URL, "파일형식이 비어있습니다.");
    }

    String[] parts = fileUrl.split("\\.amazonaws\\.com/");
    if (parts.length != 2) {
      throw new S3ImageException(ErrorCode.INVALID_FILE_URL, "잘못된 S3 URL 형식입니다");
    }
    return parts[1];
  }

  private String generateFileUrl(String fileName) {
    return String.format("https://%s.s3.amazonaws.com/%s", bucket, fileName);
  }
}
