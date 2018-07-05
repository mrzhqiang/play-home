package util;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
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
  /**
   * 距离范围。
   * <p>
   * 表示最高支持检测的范围，比如：HOURS 显示 59 分钟前，但不会是 1 小时前。以此类推。
   */
  public enum Range {
    HOURS,
    DAY,
    MONTH,
    YEAR,
    ALL
  }

  /**
   * 时间戳范围。
   * <p>
   * 表示目标时间戳距离现在，已过去多长时间。默认统计一天以内的范围。
   *
   * @param value 目标时间戳。
   * @return 对时间范围的文字描述，比如：刚刚、一分钟前。如果目标时间戳在“现在”之后，则返回 Null。
   */
  public static String range(Date value) {
    return range(value, Range.DAY);
  }

  /**
   * 时间戳范围。
   * <p>
   * 表示目标时间戳距离现在，已过去多长时间。
   *
   * @param value 目标时间戳。
   * @param range 对应的范围，{@link Range}。
   * @return 对时间范围的文字描述，比如：刚刚、一分钟前。如果目标时间戳在“现在”之后，则返回 Null。
   */
  public static String range(Date value, Range range) {
    Objects.requireNonNull(value);
    LocalDateTime nowDateTime = LocalDateTime.now(ZoneOffset.UTC);
    LocalDateTime valueDateTime = LocalDateTime.ofInstant(value.toInstant(), ZoneOffset.UTC);
    if (nowDateTime.isBefore(valueDateTime)) {
      return null;
    }
    switch (range) {
      case ALL:
        if (nowDateTime.getYear() > valueDateTime.getYear()) {
          return (nowDateTime.getYear() - valueDateTime.getYear()) + " 年前";
        }
      case YEAR:
        if (nowDateTime.getMonthValue() > valueDateTime.getMonthValue()) {
          return (nowDateTime.getMonthValue() - valueDateTime.getMonthValue()) + " 月前";
        }
      case MONTH:
        if (nowDateTime.getDayOfMonth() > valueDateTime.getDayOfMonth()) {
          return (nowDateTime.getDayOfMonth() - valueDateTime.getDayOfMonth()) + " 天前";
        }
      case DAY:
        if (nowDateTime.getHour() > valueDateTime.getHour()) {
          return (nowDateTime.getHour() - valueDateTime.getHour()) + " 小时前";
        }
      case HOURS:
        if (nowDateTime.getMinute() > valueDateTime.getMinute()) {
          return (nowDateTime.getMinute() - valueDateTime.getMinute()) + " 分钟前";
        }
      default:
        return "刚刚";
    }
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
  public static String showTime(Date value) {
    Objects.requireNonNull(value);
    LocalDateTime nowDateTime = LocalDateTime.now(ZoneOffset.UTC);
    LocalDateTime valueDateTime = LocalDateTime.ofInstant(value.toInstant(), ZoneOffset.UTC);
    if (valueDateTime.isAfter(nowDateTime)) {
      return DateHelper.formatNormal(value);
    }
    if (valueDateTime.getYear() != nowDateTime.getYear()) {
      return DateHelper.formatOtherYear(value);
    }
    if (valueDateTime.getDayOfYear() != nowDateTime.getDayOfYear()) {
      return DateHelper.formatThisYear(value);
    }
    return DateHelper.formatToday(value);
  }

  private TimeHelper() {
  }
}
