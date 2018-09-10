package util;

import com.google.common.base.Preconditions;
import java.util.regex.Pattern;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

/**
 * Link 助手。
 *
 * @author mrzhqiang
 */
@ThreadSafe
public final class LinkHelper {
  private LinkHelper() {
    throw new AssertionError("No instance.");
  }

  /**
   * 最简单的正则表达式，检查网址是否符合：
   * <p>
   * http:// 或 https:// 开头，包含大小写字母，数字，汉字 UTF-8 编码，简单的特殊字符。
   * <p>
   * 由于规则需要涉及所有网址，因此没有对格式进行匹配，这仅仅是一个判断是否带指定前缀的正则表达式。
   */
  public static final String SIMPLE_REGEX = "^(http|https)://[a-z0-9A-Z\\u4e00-\\u9fa5?&/#=%_+-.]+";

  /**
   * 简单检测指定的超链接是否符合规则。
   *
   * @param link 指定的超链接。如果传 null 或 empty，则直接返回 false。
   * @return true 符合简单规则；false 不符合。
   * @see #SIMPLE_REGEX
   */
  public static boolean simpleCheck(@Nullable String link) {
    return link != null && !link.isEmpty() && Pattern.matches(SIMPLE_REGEX, link);
  }

  /**
   * 自动补全超链接前缀。
   *
   * @param link 指定的超链接，不能为 null。
   * @param isSSL true 表示使用 https 前缀；false 表示使用 http 前缀。
   * @return 带 http 或 https 前缀的全新超链接。
   */
  @Nonnull
  public static String autoComplete(@Nonnull String link, boolean isSSL) {
    Preconditions.checkNotNull(link);
    return isSSL ? (!link.startsWith("https://") ? "https://" + link : link)
        : (!link.startsWith("http://") ? "http://" + link : link);
  }

  /**
   * 强制切换到 http 方案。
   *
   * @param link 指定的超链接，不能为 null。
   * @return 如果超链接为 https 前缀，则将前缀替换为 http；否则，调用自动补全为 http。
   */
  @Nonnull
  public static String forceHttp(@Nonnull String link) {
    Preconditions.checkNotNull(link);
    return link.startsWith("https://") ? "http://" + link.substring(8)
        : autoComplete(link, false);
  }
}
