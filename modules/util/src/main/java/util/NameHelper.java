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

  /** 默认颜色常量。 */
  private static final int DEFAULT_COLOR = 0xFF202020;
  /** 预定义颜色常量数组。 */
  private static final int[] COLORS = {
      0xFFe91e63, 0xFF9c27b0, 0xFF673ab7, 0xFF3f51b5, 0xFF5677fc, 0xFF03a9f4, 0xFF00bcd4,
      0xFF009688, 0xFFff5722, 0xFF795548, 0xFF607d8b
  };
  /**
   * 匹配第一个字符是字母，其后跟任意个单词（包括下划线 _ ）或数字。
   */
  private static final String REGEX_START_CHAR = "^[a-zA-Z][\\w\\d]*";

  /**
   * 获取名字的第一个字符（仅限于字母或数字）。
   * <p>
   * 这个方法来自<a "href"=https://github.com/siacs/Conversations>Conversations</a>。
   *
   * @param name 一个名字或其他字符串类型的值
   * @return 传入字符串的首字母，如果传入一个空串，将使用默认字符："m"。
   */
  @Nonnull
  public static String firstLetter(@Nonnull String name) {
    Preconditions.checkNotNull(name);
    for (Character c : name.toCharArray()) {
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
   * @param name 名字字符串，不能为 null，如果是空串，则返回默认常量。
   * @return ARGB 颜色常量数值。
   */
  public static int color(@Nonnull String name) {
    Preconditions.checkNotNull(name);
    if (name.trim().isEmpty()) {
      return DEFAULT_COLOR;
    }
    // 获得 name 的 hashCode，位与 0xFFFFFFFF——即取后 8 位
    // 再根据颜色数量取模，得到下标位置，返回对应的颜色值
    return COLORS[(int) ((name.hashCode() & 0xffffffffL) % COLORS.length)];
  }

  /**
   * 匹配首个字符是字母，其后跟零到任意个字母、下划线和数字。
   *
   * @param name 用户名等字符串。
   * @return true 匹配成功；false 空串或匹配失败。
   */
  public static boolean startLetter(@Nonnull String name) {
    Preconditions.checkNotNull(name);
    return !name.trim().isEmpty() && Pattern.matches(REGEX_START_CHAR, name);
  }
}
