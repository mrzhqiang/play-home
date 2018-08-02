package util;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * 统一资源定位辅助工具。
 *
 * @author mrzhqiang
 */
public final class LinkHelper {
  private LinkHelper() {
  }

  public static final String SIMPLE_REGEX = "^(http|https)://[a-z0-9A-Z\\u4e00-\\u9fa5?&/#=%_+-.]+";
  private static final Pattern SIMPLE_PATTERN = Pattern.compile(SIMPLE_REGEX);

  public static boolean simpleCheck(String link) {
    return link != null && !link.isEmpty() && SIMPLE_PATTERN.matcher(link).matches();
  }

  public static String autoComplete(String link) {
    Objects.requireNonNull(link, "link");
    return !link.startsWith("http") ? "http://" + link : link;
  }

  public static String forceHttp(String link) {
    Objects.requireNonNull(link, "link");
    return link.startsWith("https://") ? "http://" + link.substring(8) : autoComplete(link);
  }
}
