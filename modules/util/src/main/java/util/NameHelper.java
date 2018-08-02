package util;

import java.util.regex.Pattern;

/**
 * 名字辅助工具。
 * <p>
 * 用来获取名字的首字母，根据名字哈希值获取 ARGB 颜色常量。
 *
 * @author mrzhqiang
 */
public final class NameHelper {
  private NameHelper() {
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
  public static String firstLetter(String name) {
    if (name != null) {
      for (Character c : name.toCharArray()) {
        // 是否为字母或数字
        if (Character.isLetterOrDigit(c)) {
          return c.toString();
        }
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
  public static int color(String name) {
    if (name == null || name.isEmpty()) {
      return DEFAULT_COLOR;
    }
    // 计算名字的hashCode，位与0xFFFFFFFF——相当于取得最后的8位
    // 然后根据数组长度取模，得到随机的下标位置，返回预定义的颜色值
    return COLORS[(int) ((name.hashCode() & 0xffffffffL) % COLORS.length)];
  }

  /**
   * 匹配首个字符是字母，其后跟零到任意个字母、下划线和数字。
   *
   * @param name 用户名等字符串。
   * @return true 匹配成功；false 空串或匹配失败。
   */
  public static boolean startLetter(String name) {
    return name != null && !name.isEmpty() && Pattern.matches(REGEX_START_CHAR, name);
  }
}
