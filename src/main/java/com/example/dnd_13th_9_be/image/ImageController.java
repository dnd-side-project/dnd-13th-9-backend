package com.example.dnd_13th_9_be.image;

import com.example.dnd_13th_9_be.common.utils.S3Manager;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/image")
@RequiredArgsConstructor
public class ImageController {

    private final S3Manager s3Manager;

    @PostMapping
    public String uploadTest(@RequestParam(name = "file")MultipartFile file) {
        return s3Manager.upload(file);
    }
}
