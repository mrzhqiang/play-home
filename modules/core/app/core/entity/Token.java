package core.entity;

import com.google.common.base.Strings;
import core.BaseModel;
import io.ebean.annotation.Index;
import java.time.Instant;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import static core.exception.ApplicationException.badRequest;

/**
 * 令牌。
 *
 * @author mrzhqiang
 */
@Entity
@Table(name = "tokens")
public final class Token extends BaseModel {
  public static final String ACCESS_TOKEN = "access_token";
  public static final String REFRESH_TOKEN = "refresh_token";

  @Index(name = "index_token_" + ACCESS_TOKEN)
  @Column(name = ACCESS_TOKEN, unique = true, nullable = false, columnDefinition = "访问令牌，唯一，非 null。")
  public String accessToken;
  @Index(name = "index_token_" + REFRESH_TOKEN)
  @Column(name = REFRESH_TOKEN, unique = true, nullable = false, columnDefinition = "刷新令牌，唯一，非 null。")
  public String refreshToken;
  @Column(name = "expires_in", nullable = false, columnDefinition = "过期时间，非 null，单位秒。")
  public long expiresIn;

  @ManyToOne(optional = false)
  public Account account;

  /** 是否已经过期。true 表示已经过期。*/
  public boolean isExpires() {
    return modified.plusSeconds(expiresIn).isAfter(Instant.now());
  }

  @Override public void check() {
    badRequest(!Strings.isNullOrEmpty(accessToken), "Invalid accessToken.");
    badRequest(!Strings.isNullOrEmpty(refreshToken), "Invalid refreshToken.");
    badRequest(expiresIn > 0, "ExpiresIn <= 0.");
    badRequest(account != null, "Null account.");
  }

  @Override public int hashCode() {
    return Objects.hash(super.hashCode(), accessToken, refreshToken, expiresIn, account);
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
        && Objects.equals(expiresIn, other.expiresIn)
        && Objects.equals(account, other.account);
  }

  @Override public String toString() {
    return toStringHelper()
        .add("accessToken", accessToken)
        .add("refreshToken", refreshToken)
        .add("expiresIn", expiresIn)
        .add("account", account)
        .toString();
  }
}
