package com.example.dnd_13th_9_be.common.cipher;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Arrays;
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
  private static final int IV_LENGTH = 16;

  @Override
  public String encode(String plainText) {
    try {
      final SecretKeySpec keySpec = new SecretKeySpec(privateKey.getBytes(), SECRET_KEY_ALGORITHM);
      final byte[] iv = new byte[16];
      new SecureRandom().nextBytes(iv);
      final IvParameterSpec ivSpec = new IvParameterSpec(iv);

      final Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
      cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);

      final byte[] cipherText = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
      final byte[] out = new byte[iv.length + cipherText.length];
      System.arraycopy(iv, 0, out, 0, iv.length);
      System.arraycopy(cipherText, 0, out, iv.length, cipherText.length);
      return Base64.getEncoder().encodeToString(out);
    } catch (Exception e) {
      throw new BusinessException(CIPHER_ENCODE_ERROR);
    }
  }

  @Override
  public String decode(String encodedText) {
    try {
      final byte[] keyBytes = privateKey.getBytes(StandardCharsets.UTF_8);
      if (!(keyBytes.length == 16 || keyBytes.length == 24 || keyBytes.length == 32)) {
        throw new BusinessException(CIPHER_DECODE_ERROR);
      }
      final SecretKeySpec keySpec = new SecretKeySpec(keyBytes, SECRET_KEY_ALGORITHM);

      final byte[] all = Base64.getDecoder().decode(encodedText);
      if (all.length < 17) {
        throw new BusinessException(CIPHER_DECODE_ERROR);
      }
      final byte[] iv = Arrays.copyOfRange(all, 0, 16);
      final byte[] cipherText = Arrays.copyOfRange(all, 16, all.length);

      final Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
      cipher.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(iv));
      final byte[] plain = cipher.doFinal(cipherText);
      return new String(plain, StandardCharsets.UTF_8);
    } catch (Exception e) {
      throw new BusinessException(CIPHER_DECODE_ERROR);
    }
  }
}
