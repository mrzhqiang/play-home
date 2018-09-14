package util;

import com.google.common.base.Preconditions;
import java.util.regex.Pattern;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.ThreadSafe;

/**
 * Name 助手。
 *
 * @author mrzhqiang
 */
@ThreadSafe
public final class NameHelper {
  private NameHelper() {
    throw new AssertionError("No instance.");
  }

  /**
   * 默认颜色常量。
   */
  private static final int DEFAULT_COLOR = 0xFF202020;
  /**
   * 预定义颜色常量数组。
   */
  private static final int[] COLORS = {
      0xFFe91e63, 0xFF9c27b0, 0xFF673ab7, 0xFF3f51b5, 0xFF5677fc, 0xFF03a9f4, 0xFF00bcd4,
      0xFF009688, 0xFFff5722, 0xFF795548, 0xFF607d8b
  };
  /**
   * 中文的 unicode 基本字符集。
   */
  private static final String REGEX_CHINESE = "\\u4E00-\\u9FA5";
  /**
   * 英文的大小写字母。
   */
  private static final String REGEX_ENGLISH = "a-zA-Z";
  /**
   * 阿拉伯数字 0 -- 9。
   */
  private static final String REGEX_NUMBER = "0-9";

  /**
   * 获取名字的第一个字符（仅限于字母或数字）。
   * <p>
   * 这个方法来自<a "href"=https://github.com/siacs/Conversations>Conversations</a>。
   *
   * @param value 一个名字或其他字符串类型的值
   * @return 传入字符串的首字母，如果传入一个空串，将使用默认字符："m"。
   */
  @Nonnull
  public static String firstLetter(@Nonnull String value) {
    Preconditions.checkNotNull(value, "value null.");

    for (Character c : value.toCharArray()) {
      if (Character.isLetterOrDigit(c)) {
        return c.toString();
      }
    }
    // from mrzhqiang
    return "m";
  }

  /**
   * 获取名字哈希值对应的预定义 ARGB 颜色常量。
   * <p>
   * 这个方法来自<a "href"=https://github.com/siacs/Conversations>Conversations</a>。
   *
   * @param value 名字字符串，不能为 null，如果是空串，则返回默认常量。
   * @return ARGB 颜色常量数值。
   */
  public static int color(@Nonnull String value) {
    Preconditions.checkNotNull(value, "value null.");

    if (value.isEmpty()) {
      return DEFAULT_COLOR;
    }
    // 获得 name 的 hashCode，位与 0xFFFFFFFF——即取后 8 位
    // 再根据颜色数量取模，得到下标位置，返回对应的颜色值
    return COLORS[(int) ((value.hashCode() & 0xffffffffL) % COLORS.length)];
  }

  /**
   * 检查字符串是否为中文、英文、数字的名字组合。
   * <p>
   * 注意：至少要有 1 个字符匹配成功，才算符合规则。
   *
   * @param value 字符串。
   * @return true 符合规则；false 不符合规则。
   */
  public static boolean checkName(@Nonnull String value) {
    Preconditions.checkNotNull(value, "value null.");
    Preconditions.checkArgument(!value.isEmpty(), "value empty.");

    String regex = "[" + REGEX_CHINESE + REGEX_ENGLISH + REGEX_NUMBER + "]+";
    return Pattern.matches(regex, value);
  }

  /**
   * 检查字符串是否为中文、英文、数字的名字组合，并且是否在指定长度范围内。
   *
   * @param value 字符串。
   * @param minLength 最小长度。
   * @param maxLength 最大长度。
   * @return true 符合规则；false 不符合规则。
   */
  public static boolean checkName(@Nonnull String value, int minLength, int maxLength) {
    String regex = "[" + REGEX_CHINESE + REGEX_ENGLISH + REGEX_NUMBER + "]";
    return checkRegexAndLength(regex, value, minLength, maxLength);
  }

  /**
   * 检查字符串是否为中文。
   * <p>
   * 注意：至少要有 1 个字符匹配成功，才算符合规则。
   *
   * @param value 字符串。
   * @return true 符合规则；false 不符合规则。
   */
  public static boolean checkChinese(@Nonnull String value) {
    Preconditions.checkNotNull(value, "value null.");
    Preconditions.checkArgument(!value.isEmpty(), "value empty.");

    String regex = "[" + REGEX_CHINESE + "]+";
    return Pattern.matches(regex, value);
  }

  /**
   * 检查字符串是否为中文，并且是否在指定长度范围内。
   *
   * @param value 字符串。
   * @param minLength 最小长度。
   * @param maxLength 最大长度。
   * @return true 符合规则；false 不符合规则。
   */
  public static boolean checkChinese(@Nonnull String value, int minLength, int maxLength) {
    String regex = "[" + REGEX_CHINESE + "]";
    return checkRegexAndLength(regex, value, minLength, maxLength);
  }

  /**
   * 检查字符串是否为英文。
   * <p>
   * 注意：至少要有 1 个字符匹配成功，才算符合规则。
   *
   * @param value 字符串。
   * @return true 符合规则；false 不符合规则。
   */
  public static boolean checkEnglish(@Nonnull String value) {
    Preconditions.checkNotNull(value, "value null.");
    Preconditions.checkArgument(!value.isEmpty(), "value empty.");

    String regex = "[" + REGEX_ENGLISH + "]+";
    return Pattern.matches(regex, value);
  }

  /**
   * 检查字符串是否为英文，并且是否在指定长度范围内。
   *
   * @param value 字符串。
   * @param minLength 最小长度。
   * @param maxLength 最大长度。
   * @return true 符合规则；false 不符合规则。
   */
  public static boolean checkEnglish(@Nonnull String value, int minLength, int maxLength) {
    String regex = "[" + REGEX_ENGLISH + "]";
    return checkRegexAndLength(regex, value, minLength, maxLength);
  }

  /**
   * 检查字符串是否为数字组合。
   * <p>
   * 注意：至少要有 1 个字符匹配成功，才算符合规则。
   *
   * @param value 字符串。
   * @return true 符合规则；false 不符合规则。
   */
  public static boolean checkNumber(@Nonnull String value) {
    Preconditions.checkNotNull(value, "value null.");
    Preconditions.checkArgument(!value.isEmpty(), "value empty.");

    String regex = "[" + REGEX_NUMBER + "]+";
    return Pattern.matches(regex, value);
  }

  /**
   * 检查字符串是否为数字组合，并且是否在指定长度范围内。
   *
   * @param value 字符串。
   * @param minLength 最小长度。
   * @param maxLength 最大长度。
   * @return true 符合规则；false 不符合规则。
   */
  public static boolean checkNumber(@Nonnull String value, int minLength, int maxLength) {
    String regex = "[" + REGEX_NUMBER + "]";
    return checkRegexAndLength(regex, value, minLength, maxLength);
  }

  /**
   * 检查字符串是否匹配指定的正则表达式，并且是否在指定长度范围内。
   *
   * @param regex 正则表达式，不需要包含长度范围的限制，方法使用最小长度和最大长度进行拼接。
   * @param value 字符串。
   * @param minLength 最小长度。
   * @param maxLength 最大长度。
   * @return true 符合规则；false 不符合规则。
   */
  public static boolean checkRegexAndLength(@Nonnull String regex, @Nonnull String value,
      int minLength, int maxLength) {
    Preconditions.checkNotNull(regex, "regex null.");
    Preconditions.checkArgument(!regex.isEmpty(), "regex empty.");
    Preconditions.checkNotNull(value, "value null.");
    Preconditions.checkArgument(!value.isEmpty(), "value empty.");

    Preconditions.checkArgument(minLength > 0,
        "min length must be > 0: %s", minLength);
    Preconditions.checkArgument(maxLength > 0,
        "max length must be > 0: %s", minLength);
    Preconditions.checkArgument(maxLength >= minLength,
        "max length: %s must be >= min length: %s", maxLength, minLength);

    String postfix;
    if (maxLength == minLength) {
      postfix = "{" + minLength + "}";
    } else {
      postfix = "{" + minLength + "," + maxLength + "}";
    }
    return Pattern.matches(regex + postfix, value);
  }
}
