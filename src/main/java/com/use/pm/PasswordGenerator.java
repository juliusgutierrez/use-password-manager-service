package com.use.pm;

import com.google.common.hash.Hashing;
import com.use.dto.PassSettingRequestDTO;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Random;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.Validate;

/**
 * Created by julius on 02/05/2018.
 */
public class PasswordGenerator {

  private static final String UPPER_CASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
  private static final String LOWER_CASE = "abcdefghijklmnopqrstuvwxyz";
  private static final String NUMBERS = "0123456789";
  private static final String SYMBOLS = "!@#$%^&*()-_";
  private static final int DIGIT_16 = 16;

  public String makePassword(int length, boolean hasSysbol) {
    PassSettingRequestDTO settings = new PassSettingRequestDTO();
    settings.setHasSymbols(hasSysbol);
    settings.setPasswordLength(length);
    return makePassword(settings);
  }

  public String makePassword(PassSettingRequestDTO settings) {

    final String randomString = RandomStringUtils.random(settings.getPasswordLength());
    final String hexed = getHex(randomString);
    BigInteger num = convertToDecimal(hexed);

    final String characters = buildBasedCharacters(settings);
    final char[] based = characters.toCharArray();
    Integer basedLength = based.length;

    StringBuilder builder = new StringBuilder();
    for (int x = 0; x < settings.getPasswordLength(); x++) {
      num = div(num, new BigInteger(basedLength.toString()));
      int indeces = Math.abs(num.intValue() % basedLength);
      builder.append(based[indeces]);
    }

    return builder.toString();
  }


  private String getSalt(String secretKey, String service) {
    Validate.notEmpty(secretKey, "'Secret Key' should not be null");
    return getHex(secretKey, service);
  }

  private String getHex(String salt, String password) {
    return Hashing.sha256()
        .hashString(salt + password, StandardCharsets.UTF_8)
        .toString();
  }

  private String getHex(String password) {
    return Hashing.sha256()
        .hashString(password, StandardCharsets.UTF_8)
        .toString();
  }

  private BigInteger convertToDecimal(String hexed) {
    return new BigInteger(hexed, DIGIT_16);
  }

  private String buildBasedCharacters(PassSettingRequestDTO settings) {
    StringBuilder builder = new StringBuilder();
    builder.append(LOWER_CASE);
    builder.append(UPPER_CASE);
    builder.append(NUMBERS);

    if (settings.hasSymbols()) {
      builder.append(SYMBOLS);
    }

    return builder.toString();
  }

  private BigInteger div(BigInteger x, BigInteger y) {
    return new BigInteger(x.toString())
        .divide(new BigInteger(y.toString()));
  }

  public static void main(String[] args) {
    /*PasswordGenerator service = new PasswordGenerator();
    PassSettingRequestDTO settingRequest
        = new PassSettingRequestDTO
        .Builder("secret")
        .applySymbols()
        .passwordLength(12)
        .build();
    String pass = service.makePassword(settingRequest);
    System.out.println(pass);*/

    final Random r = new SecureRandom();
    byte[] salt = new byte[32];
    r.nextBytes(salt);
    String encodedSalt = Base64.getEncoder().encodeToString(salt);
    System.out.println(encodedSalt);

    encodedSalt = "MGsyENbqQxWVueLbVvnlXZXmuAt86rwAeRlwzqLCy+w=";

  }

}
