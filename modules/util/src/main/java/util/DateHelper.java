package util;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

/**
 * 日期辅助工具。
 * <p>
 * 提供日期的解析和格式化方法，通常在数据库映射中非常有用。
 *
 * @author qiang.zhang
 */
public final class DateHelper {
  private static final ThreadLocal<DateFormat> DATE_FORMAT_OTHER_YEAR =
      ThreadLocal.withInitial(() -> {
        // 格式：2017-07-05
        return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
      });

  private static final ThreadLocal<DateFormat> DATE_FORMAT_THIS_YEAR =
      ThreadLocal.withInitial(() -> {
        // 格式：07-05 15:25
        return new SimpleDateFormat("MM-dd HH:mm", Locale.getDefault());
      });

  private static final ThreadLocal<DateFormat> DATE_FORMAT_TODAY =
      ThreadLocal.withInitial(() -> {
        // 格式：15:25
        return new SimpleDateFormat("HH:mm", Locale.getDefault());
      });

  private static final ThreadLocal<DateFormat> DATE_FORMAT_NORMAL =
      ThreadLocal.withInitial(() -> {
        // 格式：2018-07-05 15:25:00
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
      });

  private static final ThreadLocal<DateFormat> DATE_FORMAT_ISO_8601_EXTEND =
      ThreadLocal.withInitial(() -> {
        // 格式：2018-07-25T15:25:00+0800
        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZ", Locale.getDefault());
      });

  /** 格式化为 GMT 时间。 */
  public static String format(Date value) {
    return HttpDate.format(Objects.requireNonNull(value));
  }

  /** 格式化为普通时间：yyyy-MM-dd HH:mm:ss。使用系统默认语言和时区。 */
  public static String formatNormal(Date value) {
    return DATE_FORMAT_NORMAL.get().format(Objects.requireNonNull(value));
  }

  /** 格式化为 UTC 时间，使用系统默认语言和时区。 */
  public static String formatUTC(Date date) {
    return DATE_FORMAT_ISO_8601_EXTEND.get().format(Objects.requireNonNull(date));
  }

  /** 格式化为今天时间：HH:mm。使用系统默认语言和时区。 */
  public static String formatToday(Date date) {
    return DATE_FORMAT_TODAY.get().format(Objects.requireNonNull(date));
  }

  /** 格式化为今年时间：MM-dd HH:mm。使用系统默认语言和时区。 */
  public static String formatThisYear(Date date) {
    return DATE_FORMAT_THIS_YEAR.get().format(Objects.requireNonNull(date));
  }

  /** 格式化为其他时间：yyyy-MM-dd。使用系统默认语言和时区。 */
  public static String formatOtherYear(Date date) {
    return DATE_FORMAT_OTHER_YEAR.get().format(Objects.requireNonNull(date));
  }

  /** 解析 GMT 时间。 */
  public static Date parse(String source) {
    return HttpDate.parse(Objects.requireNonNull(source));
  }

  /** 解析普通时间：yyyy-MM-dd HH:mm:ss。使用系统默认语言和时区。 */
  public static Date parseNormal(String value) {
    Objects.requireNonNull(value);
    ParsePosition position = new ParsePosition(0);
    Date result = DATE_FORMAT_NORMAL.get().parse(value, position);
    if (position.getIndex() == value.length()) {
      return result;
    }
    return null;
  }

  /** 解析 UTC 时间，使用系统默认语言和时区。 */
  public static Date parseUTC(String value) {
    Objects.requireNonNull(value);
    ParsePosition position = new ParsePosition(0);
    return DATE_FORMAT_ISO_8601_EXTEND.get().parse(value, position);
  }

  private DateHelper() {
  }
}
