package util;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author qiang.zhang
 */
public class RandomHelperTest {
  @Test
  public void usernameOf() {
    String s = RandomHelper.ofString(5);
    assertEquals(5, s.length());
    System.out.println(s);
  }

  @Test
  public void usernameOf1() {
    String s = RandomHelper.ofString(5, 10);
    assertTrue(s.length() >= 5 && s.length() < 10);
    System.out.println(s);
  }

  @Test
  public void numberOf() {
    String s = RandomHelper.ofNumber(5);
    assertEquals(5, s.length());
    System.out.println(s);
  }

  @Test
  public void numberOf1() {
    String s = RandomHelper.ofNumber(5, 10);
    assertTrue(s.length() >= 5 && s.length() < 10);
    System.out.println(s);
  }

  @Test
  public void lowerCaseOf() {
    String s = RandomHelper.ofLowerCase(5);
    assertEquals(5, s.length());
    System.out.println(s);
  }

  @Test
  public void lowerCaseOf1() {
    String s = RandomHelper.ofLowerCase(5, 10);
    assertTrue(s.length() >= 5 && s.length() < 10);
    System.out.println(s);
  }
  @Test
  public void upperCaseOf() {
    String s = RandomHelper.ofUpperCase(5);
    assertEquals(5, s.length());
    System.out.println(s);
  }

  @Test
  public void upperCaseOf1() {
    String s = RandomHelper.ofUpperCase(5, 10);
    assertTrue(s.length() >= 5 && s.length() < 10);
    System.out.println(s);
  }
  @Test
  public void multiThread() throws InterruptedException {
    ExecutorService service = Executors.newFixedThreadPool(100);
    List<String> lists = Lists.newArrayListWithCapacity(100 * 1000);
    for (int i = 0; i < 100; i++) {
      service.execute(() -> {
        for (int j = 0; j < 1000; j++) {
          String s = RandomHelper.ofString(110);
          assertFalse(lists.contains(s));
          synchronized (lists) {
            lists.add(s);
          }
          System.out.println(s);
        }
      });
    }
    Thread.sleep(20000);
  }

  @Test
  public void multiThread1() throws InterruptedException {
    ExecutorService service = Executors.newFixedThreadPool(10);
    List<String> lists = Lists.newArrayListWithCapacity(10 * 100);
    for (int i = 0; i < 10; i++) {
      service.execute(() -> {
        for (int j = 0; j < 100; j++) {
          String s = RandomHelper.ofNumber(6);
          assertFalse(lists.contains(s));
          synchronized (lists) {
            lists.add(s);
          }
          System.out.println(s);
        }
      });
    }
    Thread.sleep(2000);
  }
}