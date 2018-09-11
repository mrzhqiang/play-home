package util;

import java.security.SecureRandom;
import java.util.Locale;
import java.util.Random;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.ThreadSafe;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Random 助手。
 *
 * @author qiang.zhang
 */
@ThreadSafe
public final class RandomHelper {
  private RandomHelper() {
    throw new AssertionError("No instance.");
  }

  private static final String SPECIAL = "-._~+/";
  private static final String NUMBER = "1234567890";
  private static final String LOWER_CASE = "qwertyuiopasdfghjklzxcvbnm";
  private static final String UPPER_CASE = LOWER_CASE.toUpperCase(Locale.ROOT);
  private static final String CHARS = SPECIAL + LOWER_CASE + UPPER_CASE + NUMBER;

  private static final ThreadLocal<Random> RANDOM = ThreadLocal.withInitial(SecureRandom::new);

  /**
   * 通过指定长度生成随机的字符序列。
   *
   * @param length 指定长度，不能小于或等于 0。
   * @return 随机字符串，包含：大小写字母，数字，特殊字符。
   */
  @Nonnull
  public static String ofString(int length) {
    checkArgument(length > 0, "length %s must be > 0.", length);
    return ofString(length, length);
  }

  /**
   * 通过指定最小长度和最大长度，生成随机长度的随机字符序列。
   *
   * @param minLength 最小长度，不能小于或等于 0。
   * @param maxLength 最大长度，不能小于或等于 0，并且不能小于最小长度。
   * @return 随机字符串，包含：大小写字母，数字，特殊字符。
   */
  @Nonnull
  public static String ofString(int minLength, int maxLength) {
    checkArgument(minLength > 0, "min length %s must be > 0.", minLength);
    checkArgument(maxLength > 0, "max length %s must be > 0.", maxLength);
    checkArgument(maxLength >= minLength,
        "max length %s must be >= min length %s.", maxLength, minLength);

    int length = Math.max(minLength, RANDOM.get().nextInt(maxLength));
    StringBuilder builder = new StringBuilder(length);
    for (int i = 0; i < length; i++) {
      builder.append(CHARS.charAt(RANDOM.get().nextInt(CHARS.length())));
    }
    return builder.toString();
  }

  /**
   * 通过指定长度生成随机的数字序列。
   *
   * @param length 指定长度，不能小于或等于 0。
   * @return 随机字符串，仅包含：数字。
   */
  @Nonnull
  public static String ofNumber(int length) {
    checkArgument(length > 0, "length %s must be > 0.", length);
    return ofNumber(length, length);
  }

  /**
   * 通过指定最小长度和最大长度，生成随机中间值长度的随机数字序列。
   *
   * @param minLength 最小长度，不能小于或等于 0。
   * @param maxLength 最大长度，不能小于或等于 0，并且不能小于最小长度。
   * @return 随机字符串，仅包含：数字。
   */
  @Nonnull
  public static String ofNumber(int minLength, int maxLength) {
    checkArgument(minLength > 0, "min length %s must be > 0.", minLength);
    checkArgument(maxLength > 0, "max length %s must be > 0.", maxLength);
    checkArgument(maxLength >= minLength,
        "max length %s must be >= min length %s.", maxLength, minLength);

    int length = Math.max(minLength, RANDOM.get().nextInt(maxLength));
    StringBuilder builder = new StringBuilder(length);
    // 第一个数字暂不为 0
    builder.append(NUMBER.charAt(RANDOM.get().nextInt(NUMBER.length() - 1)));
    for (int i = 1; i < length; i++) {
      builder.append(NUMBER.charAt(RANDOM.get().nextInt(NUMBER.length())));
    }
    return builder.toString();
  }

  /**
   * 通过指定长度生成随机的小写字母序列。
   *
   * @param length 指定长度，不能小于或等于 0。
   * @return 随机字符串，仅包含：小写字母。
   */
  @Nonnull
  public static String ofLowerCase(int length) {
    checkArgument(length > 0, "length %s must be > 0.", length);
    return ofLowerCase(length, length);
  }

  /**
   * 通过指定最小长度和最大长度，生成随机中间值长度的随机小写字母序列。
   *
   * @param minLength 最小长度，不能小于或等于 0。
   * @param maxLength 最大长度，不能小于或等于 0，并且不能小于最小长度。
   * @return 随机字符串，仅包含：小写字母。
   */
  @Nonnull
  public static String ofLowerCase(int minLength, int maxLength) {
    checkArgument(minLength > 0, "min length %s must be > 0.", minLength);
    checkArgument(maxLength > 0, "max length %s must be > 0.", maxLength);
    checkArgument(maxLength >= minLength,
        "max length %s must be >= min length %s.", maxLength, minLength);

    int length = Math.max(minLength, RANDOM.get().nextInt(maxLength));
    StringBuilder builder = new StringBuilder(length);
    for (int i = 0; i < length; i++) {
      builder.append(LOWER_CASE.charAt(RANDOM.get().nextInt(LOWER_CASE.length())));
    }
    return builder.toString();
  }

  /**
   * 通过指定长度生成随机的大写字母序列。
   *
   * @param length 指定长度，不能小于或等于 0。
   * @return 随机字符串，仅包含：大写字母。
   */
  @Nonnull
  public static String ofUpperCase(int length) {
    checkArgument(length > 0, "length %s must be > 0.", length);
    return ofUpperCase(length, length);
  }

  /**
   * 通过指定最小长度和最大长度，生成随机中间值长度的随机大写字母序列。
   *
   * @param minLength 最小长度，不能小于或等于 0。
   * @param maxLength 最大长度，不能小于或等于 0，并且不能小于最小长度。
   * @return 随机字符串，仅包含：大写字母。
   */
  @Nonnull
  public static String ofUpperCase(int minLength, int maxLength) {
    checkArgument(minLength > 0, "min length %s must be > 0.", minLength);
    checkArgument(maxLength > 0, "max length %s must be > 0.", maxLength);
    checkArgument(maxLength >= minLength,
        "max length %s must be >= min length %s.", maxLength, minLength);

    int length = Math.max(minLength, RANDOM.get().nextInt(maxLength));
    StringBuilder builder = new StringBuilder(length);
    for (int i = 0; i < length; i++) {
      builder.append(UPPER_CASE.charAt(RANDOM.get().nextInt(UPPER_CASE.length())));
    }
    return builder.toString();
  }
}
