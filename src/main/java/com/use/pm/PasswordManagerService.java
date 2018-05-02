package com.use.pm;

import com.google.common.hash.Hashing;
import com.use.dto.PassSettingRequestDTO;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.Validate;

/**
 * Created by julius on 02/05/2018.
 */
public class PasswordManagerService {

  private static final String UPPER_CASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
  private static final String LOWER_CASE = "abcdefghijklmnopqrstuvwxyz";
  private static final String NUMBERS = "0123456789";
  private static final String SYMBOLS = "!@#$%^&*()-_";
  private static final String SECRET_KEY = "s3cr3t";
  private static final int DIGIT_16 = 16;

  public String makePassword(PassSettingRequestDTO settings) {

    final String password = RandomStringUtils.random(settings.getPasswordLength());
    final String salt = getSalt(settings.getService());
    final String hexed = getHex(salt, password);
    BigInteger num = convertToDecimal(hexed);

    final String characters = buildBasedCharacters(settings.hasSymbols());
    char[] based = characters.toCharArray();
    Integer basedLength = based.length;

    StringBuilder builder = new StringBuilder();
    for (int x = 0; x < settings.getPasswordLength(); x++) {
      num = div(num, new BigInteger(basedLength.toString()));
      int indeces = Math.abs(num.intValue() % basedLength);
      builder.append(based[indeces]);

    }

    return builder.toString();
  }


  private String getSalt(String service) {
    Validate.notNull(service, "service should not be null");
    return getHex(SECRET_KEY, service);
  }

  private String getHex(String salt, String password) {
    Validate.notNull(password, "password should not be null");
    return Hashing.sha256()
        .hashString(salt + password, StandardCharsets.UTF_8)
        .toString();
  }

  private BigInteger convertToDecimal(String hexed) {
    return new BigInteger(hexed, DIGIT_16);
  }

  private String buildBasedCharacters(final boolean hasSymbols) {
    StringBuilder builder = new StringBuilder();
    builder.append(LOWER_CASE);
    builder.append(UPPER_CASE);
    builder.append(NUMBERS);

    if (hasSymbols) {
      builder.append(SYMBOLS);
    }

    return builder.toString();
  }

  private BigInteger div(BigInteger x, BigInteger y) {
    return new BigInteger(x.toString())
        .divide(new BigInteger(y.toString()));
  }

  public static void main(String[] args) {
    PasswordManagerService service = new PasswordManagerService();
    PassSettingRequestDTO settingRequest
        = new PassSettingRequestDTO("github.com",10);
    String pass = service.makePassword(settingRequest);
    System.out.println(pass);
  }

}
