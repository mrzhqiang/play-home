package core.entity;

import com.google.common.base.Preconditions;
import core.util.Tokens;
import io.ebean.annotation.Index;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 令牌。
 *
 * @author mrzhqiang
 */
@Entity
@Table(name = "tokens")
public final class Token extends EBeanModel {
  private static final String BASE_INDEX = "index_token_";

  public static final String COL_ACCESS_TOKEN = "access_token";
  public static final String COL_REFRESH_TOKEN = "refresh_token";
  public static final String COL_EXPIRES_IN = "expires_in";

  @Index(name = BASE_INDEX + COL_ACCESS_TOKEN)
  @Column(name = COL_ACCESS_TOKEN, unique = true, nullable = false)
  public String accessToken;
  @Column(name = COL_REFRESH_TOKEN, nullable = false)
  public String refreshToken;
  @Column(name = COL_EXPIRES_IN, nullable = false)
  public Long expiresIn;

  public boolean isValid() {
    long until = created.until(Instant.now(), ChronoUnit.SECONDS);
    return until < expiresIn;
  }

  @Override public boolean checkSelf() {
    Preconditions.checkNotNull(accessToken);
    Preconditions.checkNotNull(refreshToken);
    Preconditions.checkNotNull(expiresIn);
    Preconditions.checkState(Tokens.checkExpiresIn(expiresIn));
    return true;
  }

  @Override public int hashCode() {
    return Objects.hash(super.hashCode(), accessToken, refreshToken, expiresIn);
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
        && Objects.equals(refreshToken, other.refreshToken)
        && Objects.equals(expiresIn, other.expiresIn);
  }

  @Override public String toString() {
    return toStringHelper()
        .add("访问令牌", accessToken)
        .add("刷新令牌", refreshToken)
        .add("过期时长", expiresIn)
        .toString();
  }
}
