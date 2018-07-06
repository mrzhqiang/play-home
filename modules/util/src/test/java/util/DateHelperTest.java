package util;

import java.util.Date;
import org.junit.Test;

import static org.junit.Assert.*;

public class DateHelperTest {

  @Test
  public void format() {
    try {
      String format = DateHelper.format(null);
      assertNotNull(format);
    } catch (NullPointerException ignore) {
    }
    // Thu, 05 Jul 2018 14:50:45 GMT
    System.out.println(DateHelper.format(new Date()));
  }

  @Test
  public void formatNormal() {
    try {
      String format = DateHelper.formatNormal(null);
      assertNotNull(format);
    } catch (NullPointerException ignore) {
    }
    // 2018-07-05 22:56:40
    System.out.println(DateHelper.formatNormal(new Date()));
  }

  @Test
  public void formatUTC() {
    try {
      String format = DateHelper.formatUTC(null);
      assertNotNull(format);
    } catch (NullPointerException ignore) {
    }
    // 2018-07-05T22:57:12+0800
    System.out.println(DateHelper.formatUTC(new Date()));
  }

  @Test
  public void formatToday() {
    try {
      String format = DateHelper.formatToday(null);
      assertNotNull(format);
    } catch (NullPointerException ignore) {
    }
    // 22:57
    System.out.println(DateHelper.formatToday(new Date()));
  }

  @Test
  public void formatThisMonth() {
    try {
      String format = DateHelper.formatThisMonth(null);
      assertNotNull(format);
    } catch (NullPointerException ignore) {
    }
    // 07-05 22:58
    System.out.println(DateHelper.formatThisMonth(new Date()));
  }

  @Test
  public void formatThisYear() {
    try {
      String format = DateHelper.formatThisYear(null);
      assertNotNull(format);
    } catch (NullPointerException ignore) {
    }
    // 07-05
    System.out.println(DateHelper.formatThisYear(new Date()));
  }

  @Test
  public void formatOtherYear() {
    try {
      String format = DateHelper.formatOtherYear(null);
      assertNotNull(format);
    } catch (NullPointerException ignore) {
    }
    // 2018-07-05
    System.out.println(DateHelper.formatOtherYear(new Date()));
  }

  @Test
  public void parse() {
    try {
      Date date = DateHelper.parse(null);
      assertNotNull(date);
    } catch (NullPointerException ignore) {
    }
    // 实际上就是 HttpDate.parse 方法
    String standardSource = "Thu, 05 Jul 2018 14:50:45 GMT";
    Date standardDate = DateHelper.parse(standardSource);
    assertNotNull(standardDate);
  }

  @Test
  public void parseNormal() {
    try {
      Date date = DateHelper.parseNormal(null);
      assertNotNull(date);
    } catch (NullPointerException ignore) {
    }
    String normalSource = "2018-07-05 22:56:40";
    Date normalDate = DateHelper.parseNormal(normalSource);
    assertNotNull(normalDate);
  }

  @Test
  public void parseUTC() {
    try {
      Date date = DateHelper.parseUTC(null);
      assertNotNull(date);
    } catch (NullPointerException ignore) {
    }
    String utcSource = "2018-07-05T22:57:12+0800";
    Date utcDate = DateHelper.parseUTC(utcSource);
    assertNotNull(utcDate);
  }
}