package core.util;

import com.google.common.base.Preconditions;
import core.entity.Account;
import java.time.Instant;
import java.util.Base64;
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

  public static boolean checkUsername(@Nonnull String value) {
    return value.length() > 5 && value.length() <= 16;
  }

  public static boolean checkPassword(@Nonnull String value) {
    return value.length() > 5 && value.length() <= 16;
  }

  @Nonnull
  public static Account guestOf() {
    Account account = new Account();
    account.username = RandomHelper.stringOf(16);
    account.password = RandomHelper.numberOf(6);
    account.level = Account.Level.GUEST;
    account.lastTime = Instant.now();
    account.lastDevice = Account.DEFAULT_DEVICE_UNKNOWN;
    account.token = Tokens.guestOf();
    return account;
  }

  @Nonnull
  public static Account userOf(String username, String password) {
    Preconditions.checkNotNull(username);
    Preconditions.checkArgument(checkUsername(username));
    Preconditions.checkNotNull(password);
    Preconditions.checkArgument(checkPassword(password));

    Account account = new Account();
    account.username = username;
    account.password = Base64.getEncoder().encodeToString(password.getBytes());
    account.level = Account.Level.USER;
    account.lastTime = Instant.now();
    account.lastDevice = Account.DEFAULT_DEVICE_UNKNOWN;
    account.token = Tokens.guestOf();
    return account;
  }
}
