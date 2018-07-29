package util;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * 统一资源定位辅助工具。
 *
 * @author mrzhqiang
 */
public final class UrlHelper {
  private UrlHelper() {
  }

  public static final String SIMPLE_REGEX = "^(http|https)://[a-z0-9A-Z\\u4e00-\\u9fa5?&/#=%_+-.]+";
  public static final Pattern SIMPLE_PATTERN = Pattern.compile(SIMPLE_REGEX);

  public static boolean simpleCheck(String link) {
    Objects.requireNonNull(link, "link");
    if (link.length() > 0) {
      return SIMPLE_PATTERN.matcher(link).matches();
    }
    return false;
  }

  public static String autoComplete(String link) {
    Objects.requireNonNull(link, "link");
    if (!link.startsWith("http")) {
      return "http://" + link;
    }
    return link;
  }

  public static String forceHttp(String link) {
    Objects.requireNonNull(link, "link");
    if (link.startsWith("https://")) {
      return "http://" + link.substring(8);
    }
    return autoComplete(link);
  }
}
