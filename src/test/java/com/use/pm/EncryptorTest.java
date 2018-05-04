package com.use.pm;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Created by julius on 04/05/2018.
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest(Encryptor.class)
public class EncryptorTest {

  private static final String KEY = "keyword098765432";
  private static final String IVPARAM = "1234567890000000";
  private static final String PASSWORD_RAW = "password";
  private static final String PASSWORD_ENCRYPTED = "562HvUq4RCB9ws3FSwpDVQ==";

  @Before
  public void setUp() {
    PowerMockito.mockStatic(Encryptor.class);
    PowerMockito
        .when(Encryptor.encrypt(PASSWORD_RAW, KEY, IVPARAM))
        .thenReturn(PASSWORD_ENCRYPTED);
    PowerMockito
        .when(Encryptor.decrypt(PASSWORD_ENCRYPTED, KEY, IVPARAM))
        .thenReturn(PASSWORD_RAW);
  }

  @Test
  public void testEncrypt() {
    String output = Encryptor.encrypt(PASSWORD_RAW, KEY, IVPARAM);
    assertEquals(PASSWORD_ENCRYPTED, output);
  }

  @Test
  public void testDecrypt() {
    String output = Encryptor.decrypt(PASSWORD_ENCRYPTED, KEY, IVPARAM);
    assertEquals(PASSWORD_RAW, output);
  }

}
