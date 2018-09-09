package core.util;

import com.google.common.collect.Sets;
import core.entity.Account;
import java.time.Instant;
import javax.annotation.Nonnull;
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

  /**
   * 检查账号。
   */
  public static boolean checkUsername(@Nonnull String value) {
    return value.length() > 5 && value.length() <= 16;
  }

  /**
   * 检查密码。
   */
  public static boolean checkPassword(@Nonnull String value) {
    return value.length() > 5 && value.length() <= 16;
  }

  /**
   * 游客账号。
   */
  @Nonnull
  public static Account guestOf() {
    Account account = new Account();
    account.username = RandomHelper.stringOf(16);
    account.password = RandomHelper.numberOf(6);
    account.level = Account.Level.GUEST;
    account.lastTime = Instant.now();
    account.lastDevice = Account.DEFAULT_DEVICE_UNKNOWN;
    account.treasures = Sets.newHashSet();
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
    account.treasures = Sets.newHashSet();
    return account;
  }
}
