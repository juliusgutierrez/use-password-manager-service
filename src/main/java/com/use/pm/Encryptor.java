package com.use.pm;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.spec.KeySpec;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
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
      ex.printStackTrace();
    }

    return null;
  }

  private static Cipher getCipherBy(int mode, String key, String ivParameter) throws Exception {
    IvParameterSpec ivParam = generateIvParameterSpec(ivParameter);
    SecretKeySpec secreyKey = generateKey(key);

    Cipher cipher = Cipher.getInstance(ALGORITHM);
    cipher.init(mode, secreyKey, ivParam);
    return cipher;
  }

  private static SecretKeySpec generateKey(String key) {
    return new SecretKeySpec(key.getBytes(), AES);
  }

  private static IvParameterSpec generateIvParameterSpec(String ivParameter) {
    return new IvParameterSpec(ivParameter.getBytes(StandardCharsets.UTF_8));
  }




  private static Key generateKey(String password, String keyValue, String salt) throws Exception {

    SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
    KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 256);
    SecretKey tmp = factory.generateSecret(spec);
    SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");
    return secret;
  }

  public static void main(String[] args) {
    String e = Encryptor.encrypt("password", "keyword098765432", "1234567890000000");
    System.out.println(e);
  }

}
