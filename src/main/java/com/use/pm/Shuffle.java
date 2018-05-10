package com.use.pm;

import java.util.Random;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.Validate;

/**
 * Created by julius on 10/05/2018.
 */
public class Shuffle {

  public static String doShuffle(String value) {
    Validate.notEmpty(value, "'Value should not be empty'");
    return doShuffle(value.toCharArray());
  }

  public static String doShuffle(char[] chars) {
    Random random = new Random();
    StringBuilder builder = new StringBuilder();
    int size = chars.length;
    for (int i = 0; i < size; i++) {
      int x = random.nextInt(chars.length);
      builder.append(chars[x]);
      chars = ArrayUtils.remove(chars, x);
    }
    return builder.toString();
  }

}
