package core.util;

import core.entity.Account;
import core.entity.Token;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nonnull;
import util.RandomHelper;

/**
 * 令牌工具。
 *
 * @author mrzhqiang
 */
public final class Tokens {
  private Tokens() {
    throw new AssertionError("No instance.");
  }

  // 默认的过期时间是 30 天
  private static final long TOKEN_EXPIRES_IN = TimeUnit.DAYS.toSeconds(30);
  // 参考：https://www.oauth.com/oauth2-servers/access-tokens/access-token-response/
  private static final int OAUTH_ACCESS_TOKEN_LENGTH = 32;
  private static final int OAUTH_REFRESH_TOKEN_LENGTH = 34;

  /**
   * 检查过期时间。
   */
  public static boolean checkExpiresIn(@Nonnull Long value) {
    return value == TOKEN_EXPIRES_IN;
  }

  /**
   * 令牌。
   */
  @Nonnull
  public static Token of(@Nonnull Account account) {
    Token token = new Token();
    token.accessToken = RandomHelper.stringOf(OAUTH_ACCESS_TOKEN_LENGTH);
    token.refreshToken = RandomHelper.stringOf(OAUTH_REFRESH_TOKEN_LENGTH);
    token.expiresIn = TOKEN_EXPIRES_IN;
    token.account = account;
    return token;
  }
}
