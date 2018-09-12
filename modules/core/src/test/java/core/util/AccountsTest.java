package core.util;

import org.junit.Test;
import util.RandomHelper;

import static org.junit.Assert.*;

/**
 * 账户工具测试。
 *
 * @author qiang.zhang
 */
public class AccountsTest {

  @Test
  public void checkUsername() {
    // no null
    assertFalse(Accounts.checkUsername(null));
    // no empty
    assertFalse(Accounts.checkUsername(""));
    // no < 6
    assertFalse(Accounts.checkUsername("qwert"));
    // no > 16
    assertFalse(Accounts.checkUsername("1234567890qwertyu"));
    // no chinese
    assertFalse(Accounts.checkUsername("放肆的青春啊"));
    // no whitespace
    assertFalse(Accounts.checkUsername("2874  404"));
    // bingo
    String username = RandomHelper.ofString(6, 16);
    assertTrue(Accounts.checkUsername(username));
  }

  @Test
  public void checkPassword() {
    // no null
    assertFalse(Accounts.checkPassword(null));
    // no empty
    assertFalse(Accounts.checkPassword(""));
    // no < 6
    assertFalse(Accounts.checkPassword("qwert"));
    // no > 16
    assertFalse(Accounts.checkPassword("1234567890qwertyu"));
    // no chinese
    assertFalse(Accounts.checkPassword("放肆的青春啊"));
    // no special char
    assertFalse(Accounts.checkPassword("-=-=-="));
    // no upper case
    assertFalse(Accounts.checkPassword("QWERTY"));
    // no whitespace
    assertFalse(Accounts.checkUsername("2874  404"));
    // bingo
    String password = RandomHelper.ofNumber(6, 16);
    assertTrue(Accounts.checkPassword(password));
    password = RandomHelper.ofLowerCase(6, 16);
    assertTrue(Accounts.checkPassword(password));
  }
}