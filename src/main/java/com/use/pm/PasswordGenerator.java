package com.use.pm;

import com.google.common.hash.Hashing;
import com.use.dto.PasswordSettingRequestDTO;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
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

  public String makePassword() {
    return makePassword(8, Boolean.TRUE);
  }

  /**
   * return a ramdom String just list a password
   * @param length
   * @param hasSysbol
   * @return
   */
  public String makePassword(int length, boolean hasSysbol) {
    PasswordSettingRequestDTO settings = new PasswordSettingRequestDTO();
    settings.setHasSymbols(hasSysbol);
    settings.setPasswordLength(length);
    return makePassword(settings);
  }

  /**
   * return a ramdom String just list a password
   *
   * @param settings
   * @return
   */
  public String makePassword(PasswordSettingRequestDTO settings) {

    final String randomString = RandomStringUtils.random(settings.getPasswordLength());
    final String hexed = getHex(randomString);
    BigInteger num = convertToDecimal(hexed);

    final String characters = buildBasedCharacters(settings);
    final char[] based = characters.toCharArray();
    Integer basedLength = based.length;

    StringBuilder builder = new StringBuilder();
    for (int x = 0; x < settings.getPasswordLength(); x++) {
      num = divide(num, new BigInteger(basedLength.toString()));
      int indexes = Math.abs(num.intValue() % basedLength);
      builder.append(based[indexes]);
    }

    return builder.toString();
  }


  /**
   * Generate a random string based on the parameter given
   * @param noOfDigits
   * @param noOfCapitalLetters
   * @param noOfSpecialChar
   * @param lengthOfDesiredPassword
   * @return
   */
  public String generateRandomString(int noOfDigits, int noOfCapitalLetters, int noOfSpecialChar,
      int lengthOfDesiredPassword) {

    int countOptionAvailable = noOfDigits + noOfCapitalLetters + noOfSpecialChar;
    int noOfSmallLetters = lengthOfDesiredPassword - countOptionAvailable;

    if (lengthOfDesiredPassword < countOptionAvailable) {
      throw new RuntimeException("Invalid config setup");
    }

    Random random = new Random();
    final char[] digits = getCharBasedOnCriteria(noOfDigits, NUMBERS.toCharArray(), random);
    final char[] specialChar = getCharBasedOnCriteria(noOfSpecialChar, SYMBOLS.toCharArray(),
        random);
    final char[] upperCaseLetter = getCharBasedOnCriteria(noOfCapitalLetters,
        UPPER_CASE.toCharArray(),
        random);
    final char[] lowerCaseLetter = getCharBasedOnCriteria(noOfSmallLetters,
        LOWER_CASE.toCharArray(),
        random);
    String password = Shuffle.doShuffle(
        concatArraysToString(digits, upperCaseLetter, specialChar, lowerCaseLetter));
    return password;
  }

  protected String concatArraysToString(char[]... arrayOfChar) {
    StringBuilder builder = new StringBuilder();
    for (char[] chars : arrayOfChar) {
      builder.append(chars);
    }
    return builder.toString();
  }

  protected char[] getCharBasedOnCriteria(int size, char[] chars, Random random) {
    char[] temp = new char[size];

    for (int x = 0; x < size; x++) {
      int randomInt = random.nextInt(chars.length);
      temp[x] = chars[randomInt];
    }
    return temp;
  }


  /**
   * return a hexadecimal string
   * @param secretKey
   * @param service
   * @return
   */
  public String getSalt(String secretKey, String service) {
    Validate.notEmpty(secretKey, "'Secret Key' should not be null");
    return getHex(secretKey, service);
  }

  /**return a hexadecimal string
   *
   * @param salt
   * @param password
   * @return
   */
  public String getHex(String salt, String password) {
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

  private String buildBasedCharacters(PasswordSettingRequestDTO settings) {
    StringBuilder builder = new StringBuilder();
    builder.append(LOWER_CASE);
    builder.append(UPPER_CASE);
    builder.append(NUMBERS);

    if (settings.hasSymbols()) {
      builder.append(SYMBOLS);
    }

    return builder.toString();
  }

  private BigInteger divide(BigInteger x, BigInteger y) {
    return new BigInteger(x.toString())
        .divide(new BigInteger(y.toString()));
  }

}
