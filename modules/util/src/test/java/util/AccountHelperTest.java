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
public class AccountHelperTest {

  @Test
  public void usernameOf() {
    String s = AccountHelper.usernameOf(5);
    assertEquals(5, s.length());
    System.out.println(s);
  }

  @Test
  public void usernameOf1() {
    String s = AccountHelper.usernameOf(5, 10);
    assertTrue(s.length() >= 5 && s.length() < 10);
    System.out.println(s);
  }

  @Test
  public void numberOf() {
    String s = AccountHelper.numberOf(5);
    assertEquals(5, s.length());
    System.out.println(s);
  }

  @Test
  public void numberOf1() {
    String s = AccountHelper.numberOf(5, 10);
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
          String s = AccountHelper.usernameOf(6, 16);
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
}