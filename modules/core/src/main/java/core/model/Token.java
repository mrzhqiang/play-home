package core.model;

import com.google.common.base.MoreObjects;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nonnull;
import util.RandomHelper;

/**
 * 令牌。
 * <p>
 * 通常用于身份验证流程。
 * <p>
 * 令牌有一个过期时间，如果数据意外丢失，用户重新登录即可再次生成，因此不用持久化到实体数据库。
 *
 * @author mrzhqiang
 */
public final class Token {
  /**
   * 访问令牌的过期时间：1 小时 = 3600 秒。
   * <p>
   * 理由：持续访问会刷新这个时长，一旦没有任何操作，后台将继续保持 1 个小时的活动。
   */
  public static final long ACCESS_TOKEN_EXPIRES_IN = TimeUnit.HOURS.toSeconds(1);
  /**
   * 刷新令牌的过期时间：30 天 = 2592000 秒。
   * <p>
   * 理由：如果不进行重新登录，很有可能会忘记密码，以一个月为周期非常不错。
   */
  public static final long REFRESH_TOKEN_EXPIRES_IN = TimeUnit.DAYS.toSeconds(30);

  /**
   * 存储在 Redis 中的访问令牌键名。
   */
  public static final String KEY_ACCESS_TOKEN = "access_token";
  /**
   * 存储在 Redis 中的刷新令牌键名。
   */
  public static final String KEY_REFRESH_TOKEN = "refresh_token";

  // 参考：https://www.oauth.com/oauth2-servers/access-tokens/access-token-response/
  private static final int OAUTH_ACCESS_TOKEN_LENGTH = 32;
  private static final int OAUTH_REFRESH_TOKEN_LENGTH = 34;

  private final String accessToken;
  private final String refreshToken;

  private Token(@Nonnull String accessToken, @Nonnull String refreshToken) {
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
  }

  public String getAccessToken() {
    return accessToken;
  }

  public String getRefreshToken() {
    return refreshToken;
  }

  @Override public int hashCode() {
    return Objects.hash(accessToken, refreshToken);
  }

  @Override public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (!(obj instanceof Token)) {
      return false;
    }

    Token other = (Token) obj;
    return super.equals(obj)
        && Objects.equals(accessToken, other.accessToken)
        && Objects.equals(refreshToken, other.refreshToken);
  }

  @Override public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("访问令牌", accessToken)
        .add("刷新令牌", refreshToken)
        .toString();
  }

  @Nonnull
  @CanIgnoreReturnValue
  public static Token ofRefresh(@Nonnull String refreshToken) {
    String accessToken = RandomHelper.ofString(OAUTH_ACCESS_TOKEN_LENGTH);
    return new Token(accessToken, refreshToken);
  }

  @Nonnull
  @CanIgnoreReturnValue
  public static Token ofLogin() {
    String accessToken = RandomHelper.ofString(OAUTH_ACCESS_TOKEN_LENGTH);
    String refreshToken = RandomHelper.ofString(OAUTH_REFRESH_TOKEN_LENGTH);
    return new Token(accessToken, refreshToken);
  }
}
