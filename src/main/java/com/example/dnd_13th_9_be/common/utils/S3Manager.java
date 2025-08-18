package com.example.dnd_13th_9_be.common.utils;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.dnd_13th_9_be.global.error.BusinessException;
import com.example.dnd_13th_9_be.global.error.ErrorCode;
import com.example.dnd_13th_9_be.global.error.S3ImageException;
import com.google.common.base.Strings;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class S3Manager {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String BUCKET;

    public String upload(MultipartFile file){
        String fileName = createUniqueFileName(file.getOriginalFilename());

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());

        amazonS3Client.putObject(BUCKET, fileName, getInputStream(file), metadata);
        return amazonS3Client.getUrl(BUCKET, fileName).toString();
    }

    public String getImageUrl(String fileKey){
        return amazonS3Client.getUrl(BUCKET, fileKey).toString();
    }

    public String update(String oldFileUrl, MultipartFile newFile) {
        delete(oldFileUrl);
        return upload(newFile);
    }

    public void delete(String fileUrl) {
        if(!StringUtils.hasText(fileUrl)){
            throw new BusinessException(ErrorCode.FILE_NOT_FOUND);
        }
        String fileKey = extractFileKey(fileUrl);
        amazonS3Client.deleteObject(BUCKET, fileKey);
    }

    public void deleteMultiple(List<String> fileUrls) {
        fileUrls.forEach(this::delete);
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
            throw new S3ImageException(ErrorCode.INVALID_FILE_URL,"파일형식이 비어있습니다.");
        }

        String[] parts = fileUrl.split(".amazonaws.com/");
        if (parts.length != 2) {
            throw new S3ImageException(ErrorCode.INVALID_FILE_URL,"잘못된 S3 URL 형식입니다");
        }
        return parts[1];
    }

    private InputStream getInputStream(MultipartFile file) {
        try {
            return file.getInputStream();
        } catch (IOException e) {
            throw new S3ImageException(ErrorCode.INVALID_FILE_URL, "파일 입력 스트림을 가져올 수 없습니다.");
        }
    }


}
