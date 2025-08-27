package com.example.dnd_13th_9_be.common.cipher;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AESCipherUtil implements CipherUtil {
    @Value("${aes.private-key}")
    private String privateKey;

    public static String ALGORITHMS = "AES/CBC/PKCS5Padding";

    @Override
    public String encode(String plainText) {
        return null;
    }

    @Override
    public String decode(String encodedText) {
        return null;
    }
}
