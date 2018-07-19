package com.github.mrzhqiang.util;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

/**
 * Date Helper.
 * <p>
 * If you don't like the {@link Date} that you can use the {@link java.time.format.DateTimeFormatter}
 * in JDK 1.8.
 *
 * @author qiang.zhang
 */
@ThreadSafe
public final class DateHelper {
  private DateHelper() {
  }

  /** 2018-07-05 15:25:00 */
  private static final ThreadLocal<DateFormat> DATE_FORMAT_NORMAL =
      ThreadLocal.withInitial(
          () -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()));

  /** Thu, 05 Jul 2018 14:50:45 GMT */
  @Nonnull
  @CheckReturnValue
  public static String format(Date value) {
    return HttpDate.format(Objects.requireNonNull(value));
  }

  /** 2018-07-05 22:56:40 */
  @Nonnull
  @CheckReturnValue
  public static String formatNormal(Date value) {
    return DATE_FORMAT_NORMAL.get().format(Objects.requireNonNull(value));
  }

  /** Returns the date for {@code value}. Returns null if the value couldn't be parsed. */
  @Nullable
  @CheckReturnValue
  public static Date parse(String value) {
    return HttpDate.parse(Objects.requireNonNull(value));
  }

  /** yyyy-MM-dd HH:mm:ss, use Locale.getDefault() and TimeZone.getDefault(). */
  @Nullable
  @CheckReturnValue
  public static Date parseNormal(String value) {
    Objects.requireNonNull(value);
    ParsePosition position = new ParsePosition(0);
    Date result = DATE_FORMAT_NORMAL.get().parse(value, position);
    if (position.getIndex() == value.length()) {
      return result;
    }
    return null;
  }
}
