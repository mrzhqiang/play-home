package core.util;

import core.entity.Account;
import java.time.Instant;
import java.util.regex.Pattern;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import util.RandomHelper;

/**
 * 账户工具。
 *
 * @author mrzhqiang
 */
public final class Accounts {
  private Accounts() {
    throw new AssertionError("No instance.");
  }

  private static final String REGEX_USERNAME = "[a-zA-Z0-9._~+/-]{6,16}";
  private static final String REGEX_PASSWORD = "[a-z0-9]{6,16}";

  /**
   * 检查账号。
   */
  public static boolean checkUsername(@Nullable String value) {
    return value != null && Pattern.matches(REGEX_USERNAME, value);
  }

  /**
   * 检查密码。
   */
  public static boolean checkPassword(@Nullable String value) {
    return value != null && Pattern.matches(REGEX_PASSWORD, value);
  }

  /**
   * 游客账号。
   */
  @Nonnull
  public static Account ofGuest() {
    Account account = new Account();
    account.username = RandomHelper.ofString(16);
    account.password = RandomHelper.ofNumber(6);
    account.level = Account.Level.GUEST;
    account.lastTime = Instant.now();
    account.lastDevice = Account.DEFAULT_DEVICE_UNKNOWN;
    return account;
  }

  /**
   * 用户账号。
   */
  @Nonnull
  public static Account of(@Nonnull String username, @Nonnull String password) {
    Account account = new Account();
    account.username = username;
    account.password = password;
    account.level = Account.Level.USER;
    account.lastTime = Instant.now();
    account.lastDevice = Account.DEFAULT_DEVICE_UNKNOWN;
    return account;
  }
}