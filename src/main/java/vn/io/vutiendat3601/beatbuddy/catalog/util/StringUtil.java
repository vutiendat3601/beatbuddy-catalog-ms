package vn.io.vutiendat3601.beatbuddy.catalog.util;

public class StringUtil {
  private static final int DEFAULT_RANDOM_LENGTH = 16;
  private static final String CHARS =
      "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

  public static String randomString(int length) {
    final int CHARS_LENGTH = CHARS.length();
    StringBuilder randomString = new StringBuilder();
    for (int i = 0; i < length; i++) {
      randomString.append(CHARS.charAt((int) (Math.random() * CHARS_LENGTH)));
    }
    return randomString.toString();
  }

  public static String randomString() {
    return randomString(DEFAULT_RANDOM_LENGTH);
  }
}
