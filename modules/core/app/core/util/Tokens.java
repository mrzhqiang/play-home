package core.util;

import com.google.common.collect.Sets;
import core.entity.Token;
import core.entity.User;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;
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

  // 这里由于 Token 只给 APP 使用，所以默认的过期时间是 30 天
  private static final long TOKEN_EXPIRES_IN = TimeUnit.DAYS.toSeconds(30);
  // 这部分参考的是：https://www.oauth.com/oauth2-servers/access-tokens/access-token-response/
  private static final int OAUTH_ACCESS_TOKEN_LENGTH = 32;
  private static final int OAUTH_REFRESH_TOKEN_LENGTH = 34;

  public static Token guestOf() {
    Token token = new Token();
    token.accessToken = RandomHelper.stringOf(OAUTH_ACCESS_TOKEN_LENGTH);
    token.refreshToken = RandomHelper.stringOf(OAUTH_REFRESH_TOKEN_LENGTH);
    token.expiresIn = TOKEN_EXPIRES_IN;
    token.user = new User();
    token.user.nickname = "游客";
    token.user.avatar = "";
    token.treasures = Sets.newHashSet();
  }
}
