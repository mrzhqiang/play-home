package com.github.mrzhqiang.util;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TimeHelperTest {

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
    monthInstant = nowInstant.minus(Duration.ofDays(31));
    yearInstant = nowInstant.minus(Duration.ofDays(365));
  }

  @Test
  public void between() {
    try {
      String between = TimeHelper.betweenNow(null);
      assertNotNull(between);
    } catch (NullPointerException ignore) {
    }

    System.out.println(TimeHelper.betweenNow(Date.from(nowInstant)));
    System.out.println(TimeHelper.betweenNow(Date.from(minutesInstant)));
    System.out.println(TimeHelper.betweenNow(Date.from(hoursInstant)));
    System.out.println(TimeHelper.betweenNow(Date.from(dayInstant)));
    System.out.println(TimeHelper.betweenNow(Date.from(monthInstant)));
    System.out.println(TimeHelper.betweenNow(Date.from(yearInstant)));
  }

  @Test
  public void showTime() {
    try {
      String display = TimeHelper.display(null);
      assertNotNull(display);
    } catch (NullPointerException ignore) {
    }

    System.out.println(TimeHelper.display(Date.from(nowInstant)));
    System.out.println(TimeHelper.display(Date.from(minutesInstant)));
    System.out.println(TimeHelper.display(Date.from(hoursInstant)));
    System.out.println(TimeHelper.display(Date.from(dayInstant)));
    System.out.println(TimeHelper.display(Date.from(monthInstant)));
    System.out.println(TimeHelper.display(Date.from(yearInstant)));
  }
}