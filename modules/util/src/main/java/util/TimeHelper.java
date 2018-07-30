package util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Objects;

/**
 * 时间戳辅助工具。
 * <p>
 * 通常用于即时聊天、论坛发帖等需要显示时间戳的场景。
 *
 * @author mrzhqiang
 */
public final class TimeHelper {
  private TimeHelper() {
  }

  private static final String FORMAT_BETWEEN_YEARS = "%d 年前";
  private static final String FORMAT_BETWEEN_MONTHS = "%d 个月前";
  private static final String FORMAT_BETWEEN_DAYS = "%d 天前";
  private static final String FORMAT_BETWEEN_HOURS = "%d 小时前";
  private static final String FORMAT_BETWEEN_MINUTES = "%d 分钟前";
  private static final String NOW = "刚刚";

  /**
   * 时间戳与现在的间隔。
   * <p>
   * 表示目标时间戳距离现在，已过去多长时间。
   * <p>
   * 换算：
   * <pre>
   * 1 year = 12 month
   * 1 month = 365.2425 day
   * 1 day = 24 hour
   * 1 hour = 60 minute
   * 1 minute = 60 second
   * </pre>
   *
   * @param value 目标时间戳。
   * @return 对时间间隔的文字描述，比如：刚刚、1 分钟前。
   */
  public static String betweenNow(Date value) {
    Objects.requireNonNull(value);
    LocalDateTime nowTime = LocalDateTime.now();
    LocalDateTime valueTime = LocalDateTime.ofInstant(value.toInstant(), ZoneId.systemDefault());
    long betweenYears = ChronoUnit.YEARS.between(valueTime, nowTime);
    if (betweenYears != 0) {
      return String.format(FORMAT_BETWEEN_YEARS, betweenYears);
    }
    long betweenMonth = ChronoUnit.MONTHS.between(valueTime, nowTime);
    if (betweenMonth != 0) {
      return String.format(FORMAT_BETWEEN_MONTHS, betweenMonth);
    }
    long betweenDays = ChronoUnit.DAYS.between(valueTime, nowTime);
    if (betweenDays != 0) {
      return String.format(FORMAT_BETWEEN_DAYS, betweenDays);
    }
    long betweenHours = ChronoUnit.HOURS.between(valueTime, nowTime);
    if (betweenHours != 0) {
      return String.format(FORMAT_BETWEEN_HOURS, betweenHours);
    }
    long betweenMinutes = ChronoUnit.MINUTES.between(valueTime, nowTime);
    if (betweenMinutes != 0) {
      return String.format(FORMAT_BETWEEN_MINUTES, betweenMinutes);
    }
    return NOW;
  }

  /**
   * 显示时间戳。
   * <p>
   * 规则：
   * 超过现在：{@link DateHelper#formatNormal(Date)}；
   * 今天以内：{@link DateTimeFormatter#ISO_LOCAL_DATE}；
   * 其他时间：{@link DateTimeFormatter#ISO_LOCAL_TIME}。
   *
   * @param value 目标时间戳。
   * @return 格式化过的时间戳字符串
   */
  public static String display(Date value) {
    Objects.requireNonNull(value);
    LocalDateTime nowTime = LocalDateTime.now();
    LocalDateTime valueTime = LocalDateTime.ofInstant(value.toInstant(), ZoneId.systemDefault());
    if (valueTime.isAfter(nowTime)) {
      // yyyy-MM-dd HH:mm:ss
      return DateHelper.formatNormal(value);
    }
    long betweenDays = ChronoUnit.DAYS.between(valueTime, nowTime);
    if (betweenDays == 0) {
      // HH:mm:ss
      return DateTimeFormatter.ISO_LOCAL_TIME.format(valueTime);
    }
    // yyyy-MM-dd
    return DateTimeFormatter.ISO_LOCAL_DATE.format(valueTime);
  }
}
