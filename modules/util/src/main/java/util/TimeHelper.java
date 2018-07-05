package util;

import java.time.LocalDateTime;
import java.time.ZoneId;
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

  private static final String FORMAT_BETWEEN_YEARS = "%d 年之前";
  private static final String FORMAT_BETWEEN_DAYS = "%d 天以前";
  private static final String FORMAT_BETWEEN_HOURS = "%d 小时前";
  private static final String FORMAT_BETWEEN_MINUTES = "%d 分钟前";
  private static final String NOW = "刚刚";

  /**
   * 时间戳间隔。
   * <p>
   * 表示目标时间戳距离现在，已过去多长时间。
   *
   * @param value 目标时间戳。
   * @return 对时间间隔的文字描述，比如：刚刚、1 分钟前。
   */
  public static String between(Date value) {
    Objects.requireNonNull(value);
    LocalDateTime nowTime = LocalDateTime.now();
    LocalDateTime valueTime = LocalDateTime.ofInstant(value.toInstant(), ZoneId.systemDefault());
    long betweenYears = ChronoUnit.YEARS.between(valueTime, nowTime);
    if (betweenYears != 0) {
      return String.format(FORMAT_BETWEEN_YEARS, betweenYears);
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
   * 当时间超过现在，显示正常格式；
   * 当时间在今天以内，显示粒度细化到 HH:mm；
   * 当时间在今年以内，显示粒度细化到 MM-dd HH:mm；
   * 其他时间范围，显示粒度细化到 yyyy-MM-dd HH:mm。
   *
   * @param value 目标时间戳。
   * @return 格式化过的时间戳字符串
   */
  public static String display(Date value) {
    Objects.requireNonNull(value);
    LocalDateTime nowTime = LocalDateTime.now();
    LocalDateTime valueTime = LocalDateTime.ofInstant(value.toInstant(), ZoneId.systemDefault());
    if (valueTime.isAfter(nowTime)) {
      return DateHelper.formatNormal(value);
    }
    if (valueTime.getYear() != nowTime.getYear()) {
      return DateHelper.formatOtherYear(value);
    }
    if (valueTime.getDayOfYear() != nowTime.getDayOfYear()) {
      return DateHelper.formatThisYear(value);
    }
    return DateHelper.formatToday(value);
  }

  private TimeHelper() {
  }
}
