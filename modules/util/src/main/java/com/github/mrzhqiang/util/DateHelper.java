package com.github.mrzhqiang.util;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

/**
 * Date Helper.
 * <p>
 * If you don't like the {@link Date} that you can use the {@link java.time.format.DateTimeFormatter}
 * in JDK 1.8.
 *
 * @author qiang.zhang
 */
public final class DateHelper {

  /** 2017-07-05 */
  private static final ThreadLocal<DateFormat> DATE_FORMAT_OTHER_YEAR =
      ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()));

  /** 07-05 */
  private static final ThreadLocal<DateFormat> DATE_FORMAT_THIS_YEAR =
      ThreadLocal.withInitial(() -> new SimpleDateFormat("MM-dd", Locale.getDefault()));

  /** 07-05 15:25 */
  private static final ThreadLocal<DateFormat> DATE_FORMAT_THIS_MONTH =
      ThreadLocal.withInitial(() -> new SimpleDateFormat("MM-dd HH:mm", Locale.getDefault()));

  /** 15:25 */
  private static final ThreadLocal<DateFormat> DATE_FORMAT_TODAY =
      ThreadLocal.withInitial(() -> new SimpleDateFormat("HH:mm", Locale.getDefault()));

  /** 2018-07-05 15:25:00 */
  private static final ThreadLocal<DateFormat> DATE_FORMAT_NORMAL =
      ThreadLocal.withInitial(
          () -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()));

  /** 2018-07-25T15:25:00+0800 */
  private static final ThreadLocal<DateFormat> DATE_FORMAT_ISO_8601_EXTEND =
      ThreadLocal.withInitial(
          () -> new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZ", Locale.getDefault()));

  /** Thu, 05 Jul 2018 14:50:45 GMT */
  public static String format(Date value) {
    return HttpDate.format(Objects.requireNonNull(value));
  }

  /** 2018-07-05 22:56:40 */
  public static String formatNormal(Date value) {
    return DATE_FORMAT_NORMAL.get().format(Objects.requireNonNull(value));
  }

  /** 2018-07-05T22:57:12+0800 */
  public static String formatUTC(Date value) {
    return DATE_FORMAT_ISO_8601_EXTEND.get().format(Objects.requireNonNull(value));
  }

  /** Returns the date for {@code value}. Returns null if the value couldn't be parsed. */
  public static Date parse(String source) {
    return HttpDate.parse(Objects.requireNonNull(source));
  }

  /** yyyy-MM-dd HH:mm:ss, use Locale.getDefault() and TimeZone.getDefault(). */
  public static Date parseNormal(String value) {
    Objects.requireNonNull(value);
    ParsePosition position = new ParsePosition(0);
    Date result = DATE_FORMAT_NORMAL.get().parse(value, position);
    if (position.getIndex() == value.length()) {
      return result;
    }
    return null;
  }

  /** yyyy-MM-dd'T'HH:mm:ssZZ, use Locale.getDefault() and TimeZone.getDefault(). */
  public static Date parseUTC(String value) {
    Objects.requireNonNull(value);
    ParsePosition position = new ParsePosition(0);
    return DATE_FORMAT_ISO_8601_EXTEND.get().parse(value, position);
  }

  /** HH:mm, use Locale.getDefault() and TimeZone.getDefault(). */
  static String formatToday(Date value) {
    return DATE_FORMAT_TODAY.get().format(Objects.requireNonNull(value));
  }

  /** MM-dd HH:mm, use Locale.getDefault() and TimeZone.getDefault(). */
  static String formatThisMonth(Date value) {
    return DATE_FORMAT_THIS_MONTH.get().format(Objects.requireNonNull(value));
  }

  /** MM-dd, use Locale.getDefault() and TimeZone.getDefault(). */
  static String formatThisYear(Date value) {
    return DATE_FORMAT_THIS_YEAR.get().format(Objects.requireNonNull(value));
  }

  /** yyyy-MM-dd, use Locale.getDefault() and TimeZone.getDefault(). */
  static String formatOtherYear(Date date) {
    return DATE_FORMAT_OTHER_YEAR.get().format(Objects.requireNonNull(date));
  }

  private DateHelper() {
  }
}
