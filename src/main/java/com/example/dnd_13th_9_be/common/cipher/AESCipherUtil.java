package com.example.dnd_13th_9_be.common.cipher;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.dnd_13th_9_be.global.error.BusinessException;

import static com.example.dnd_13th_9_be.global.error.ErrorCode.CIPHER_DECODE_ERROR;
import static com.example.dnd_13th_9_be.global.error.ErrorCode.CIPHER_ENCODE_ERROR;

@Component
public class AESCipherUtil implements CipherUtil {
  @Value("${aes.private-key}")
  private String privateKey;

  public static String CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";
  public static String SECRET_KEY_ALGORITHM = "AES";

  @Override
  public String encode(String plainText) {
    try {
      final SecretKeySpec keySpec = new SecretKeySpec(privateKey.getBytes(), SECRET_KEY_ALGORITHM);
      final IvParameterSpec IV = new IvParameterSpec(privateKey.substring(0, 16).getBytes());

      final Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
      cipher.init(Cipher.ENCRYPT_MODE, keySpec, IV);

      byte[] encrypted = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
      return Base64.getEncoder().encodeToString(encrypted);
    } catch (Exception e) {
      throw new BusinessException(CIPHER_ENCODE_ERROR);
    }
  }

  @Override
  public String decode(String encodedText) {
    try {
      final SecretKeySpec keySpec = new SecretKeySpec(privateKey.getBytes(), SECRET_KEY_ALGORITHM);
      final IvParameterSpec IV = new IvParameterSpec(privateKey.substring(0, 16).getBytes());

      final Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
      cipher.init(Cipher.DECRYPT_MODE, keySpec, IV);

      byte[] decodedBytes = Base64.getDecoder().decode(encodedText);
      byte[] decrypted = cipher.doFinal(decodedBytes);
      return new String(decrypted, StandardCharsets.UTF_8);
    } catch (Exception e) {
      throw new BusinessException(CIPHER_DECODE_ERROR);
    }
  }
}
