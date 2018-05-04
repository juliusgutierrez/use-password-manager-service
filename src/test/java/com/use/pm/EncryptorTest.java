package com.use.pm;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;

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

  private static final String PASSWORD_RAW = "password";
  private static final String PASSWORD_ENCRYPTED = "562HvUq4RCB9ws3FSwpDVQ==";

  @Before
  public void setUp() {
    PowerMockito.mockStatic(Encryptor.class);
    PowerMockito
        .when(Encryptor.encrypt(eq(PASSWORD_RAW), anyString(), anyString()))
        .thenReturn(PASSWORD_ENCRYPTED);
    PowerMockito
        .when(Encryptor.decrypt(eq(PASSWORD_ENCRYPTED), anyString(), anyString()))
        .thenReturn(PASSWORD_RAW);
  }

  @Test
  public void testEncrypt() {
    String output = Encryptor.encrypt(PASSWORD_RAW, "key", "param");
    assertEquals(PASSWORD_ENCRYPTED, output);
  }

  @Test
  public void testDecrypt() {
    String output = Encryptor.decrypt(PASSWORD_ENCRYPTED, "key", "param");
    assertEquals(PASSWORD_RAW, output);
  }

}
