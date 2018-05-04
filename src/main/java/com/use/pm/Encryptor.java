package com.use.pm;

import com.use.exception.EncryptException;
import com.use.exception.ErrorCode;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by julius on 03/05/2018.
 */
public class Encryptor {

  private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
  private static final String AES = "AES";

  public static String encrypt(String value, String key, String ivParameter) {
    try {
      Cipher cipher = getCipherBy(Cipher.ENCRYPT_MODE, key, ivParameter);
      byte[] encrypted = cipher.doFinal(value.getBytes());
      return Base64.getEncoder().encodeToString(encrypted);
    } catch (Exception ex) {
      throw new EncryptException(ErrorCode.ENCRYPTION_ERROR);
    }
  }

  public static String decrypt(String value, String key, String ivParameter) {
    try {
      Cipher cipher = getCipherBy(Cipher.DECRYPT_MODE, key, ivParameter);
      byte[] encrypted = cipher.doFinal(Base64.getDecoder().decode(value));
      return new String(encrypted);
    } catch (Exception ex) {
      throw new EncryptException(ErrorCode.DECRYPTION_ERROR);
    }
  }

  private static Cipher getCipherBy(int mode, String key, String ivParameter) throws Exception {
    IvParameterSpec ivParam = generateIvParameterSpec(ivParameter);
    SecretKeySpec secretKey = generateKey(key);
    Cipher cipher = Cipher.getInstance(ALGORITHM);
    cipher.init(mode, secretKey, ivParam);
    return cipher;
  }

  private static SecretKeySpec generateKey(String key) throws Exception {
    return new SecretKeySpec(key.getBytes(), AES);
  }

  private static IvParameterSpec generateIvParameterSpec(String ivParameter) {
    return new IvParameterSpec(ivParameter.getBytes(StandardCharsets.UTF_8));
  }

  public static void main(String[] args) {
    String e = Encryptor.encrypt("password", "keyword098765432", "1234567890000000");
    System.out.println(e);
    System.out.println(Encryptor.decrypt(e, "keyword098765432", "1234567890000000"));
  }

}
