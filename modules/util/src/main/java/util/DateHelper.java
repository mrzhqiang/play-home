package util;

import com.google.common.base.Preconditions;
import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.ThreadSafe;

/**
 * Date 助手.
 * <p>
 * 如果你不喜欢 {@link Date} 的话，推荐用 {@link java.time.format.DateTimeFormatter} 进行格式化。
 *
 * @author qiang.zhang
 */
@ThreadSafe
public final class DateHelper {
  private DateHelper() {
    throw new AssertionError("No instance.");
  }

  /** 2018-07-05 15:25:00 */
  private static final ThreadLocal<DateFormat> DATE_FORMAT_NORMAL = ThreadLocal.withInitial(
      () -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()));

  /** Thu, 05 Jul 2018 14:50:45 GMT */
  @Nonnull
  public static String format(Date value) {
    Preconditions.checkNotNull(value);
    return HttpDate.format(value);
  }

  /** 2018-07-05 22:56:40 */
  @Nonnull
  public static String formatNormal(Date value) {
    Preconditions.checkNotNull(value);
    return DATE_FORMAT_NORMAL.get().format(value);
  }

  /** Returns the date for {@code value}. Returns null if the value couldn't be parsed. */
  @CheckForNull
  public static Date parse(String value) {
    Preconditions.checkNotNull(value);
    return HttpDate.parse(value);
  }

  /** yyyy-MM-dd HH:mm:ss, use Locale.getDefault() and TimeZone.getDefault(). */
  @CheckForNull
  public static Date parseNormal(String value) {
    Preconditions.checkNotNull(value);
    ParsePosition position = new ParsePosition(0);
    Date result = DATE_FORMAT_NORMAL.get().parse(value, position);
    if (position.getIndex() == value.length()) {
      return result;
    }
    return null;
  }
}
