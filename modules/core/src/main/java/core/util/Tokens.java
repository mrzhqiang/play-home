package core.util;

import core.entity.Account;
import core.entity.Token;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
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
   * 检查访问令牌。
   */
  public static boolean checkAccessToken(@Nullable String accessToken) {
    return accessToken != null && accessToken.length() == OAUTH_ACCESS_TOKEN_LENGTH;
  }

  /**
   * 检查刷新令牌。
   */
  public static boolean checkRefreshToken(@Nullable String refreshToken) {
    return refreshToken != null && refreshToken.length() == OAUTH_REFRESH_TOKEN_LENGTH;
  }

  /**
   * 检查过期时间。
   */
  public static boolean checkExpiresIn(@Nullable Long value) {
    return value != null && value == TOKEN_EXPIRES_IN;
  }

  /**
   * 令牌。
   */
  @Nonnull
  public static Token of(@Nonnull Account account) {
    Token token = new Token();
    token.accessToken = RandomHelper.ofString(OAUTH_ACCESS_TOKEN_LENGTH);
    token.refreshToken = RandomHelper.ofString(OAUTH_REFRESH_TOKEN_LENGTH);
    token.expiresIn = TOKEN_EXPIRES_IN;
    token.account = account;
    return token;
  }
}
