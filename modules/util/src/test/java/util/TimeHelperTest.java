package util;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static java.time.format.DateTimeFormatter.*;

/**
 * @author mrzhqiang
 */
public final class TimeHelperTest {
  private Instant nowInstant;
  private Instant minutesInstant;
  private Instant hoursInstant;
  private Instant dayInstant;
  private Instant monthInstant;
  private Instant yearInstant;

  @Before
  public void before() {
    nowInstant = Instant.now();
    minutesInstant = nowInstant.minus(Duration.ofMinutes(1));
    hoursInstant = nowInstant.minus(Duration.ofHours(1));
    dayInstant = nowInstant.minus(Duration.ofDays(1));
    monthInstant = nowInstant.minus(Duration.ofDays(62));
    yearInstant = nowInstant.minus(Duration.ofDays(365));
  }

  @Test
  public void between() {
    try {
      String between = TimeHelper.betweenNow(null);
      assertNotNull(between);
    } catch (NullPointerException ignore) {
    }

    assertEquals("刚刚", TimeHelper.betweenNow(Date.from(nowInstant)));
    assertEquals("1 分钟前", TimeHelper.betweenNow(Date.from(minutesInstant)));
    assertEquals("1 小时前", TimeHelper.betweenNow(Date.from(hoursInstant)));
    assertEquals("1 天前", TimeHelper.betweenNow(Date.from(dayInstant)));
    assertEquals("2 个月前", TimeHelper.betweenNow(Date.from(monthInstant)));
    assertEquals("1 年前", TimeHelper.betweenNow(Date.from(yearInstant)));
  }

  @Test
  public void showTime() {
    try {
      String display = TimeHelper.display(null);
      assertNotNull(display);
    } catch (NullPointerException ignore) {
    }

    assertEquals(ISO_LOCAL_TIME.format(nowInstant.atZone(ZoneId.systemDefault())),
        TimeHelper.display(Date.from(nowInstant)));
    assertEquals(ISO_LOCAL_TIME.format(minutesInstant.atZone(ZoneId.systemDefault())),
        TimeHelper.display(Date.from(minutesInstant)));
    assertEquals(ISO_LOCAL_TIME.format(hoursInstant.atZone(ZoneId.systemDefault())),
        TimeHelper.display(Date.from(hoursInstant)));
    assertEquals(ISO_LOCAL_DATE.format(dayInstant.atZone(ZoneId.systemDefault())),
        TimeHelper.display(Date.from(dayInstant)));
    assertEquals(ISO_LOCAL_DATE.format(monthInstant.atZone(ZoneId.systemDefault())),
        TimeHelper.display(Date.from(monthInstant)));
    assertEquals(ISO_LOCAL_DATE.format(yearInstant.atZone(ZoneId.systemDefault())),
        TimeHelper.display(Date.from(yearInstant)));
  }
}