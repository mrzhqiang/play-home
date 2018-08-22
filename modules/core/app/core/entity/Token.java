package core.entity;

import com.google.common.base.Preconditions;
import core.EBeanModel;
import io.ebean.annotation.Index;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 令牌。
 * <p>
 * 持久化访问令牌、刷新令牌和过期时间。
 * <p>
 * 另有账户信息，以多对一方式存在。
 *
 * @author mrzhqiang
 */
@Entity
@Table(name = "tokens")
public final class Token extends EBeanModel {
  public static final String ACCESS_TOKEN = "access_token";
  public static final String REFRESH_TOKEN = "refresh_token";

  @Index(name = "index_token_" + ACCESS_TOKEN)
  @Column(name = ACCESS_TOKEN, unique = true, nullable = false, columnDefinition = "访问令牌，唯一，非空。")
  public String accessToken;
  @Column(name = REFRESH_TOKEN, nullable = false, columnDefinition = "刷新令牌，非空。")
  public String refreshToken;
  @Column(name = "expires_in", nullable = false, columnDefinition = "过期时间，非空，单位秒。")
  public Long expiresIn;

  @ManyToOne(optional = false)
  public Account account;

  @Override public boolean checkSelf() {
    Objects.requireNonNull(accessToken);
    Objects.requireNonNull(refreshToken);
    Objects.requireNonNull(expiresIn);
    Objects.requireNonNull(account);
    Preconditions.checkState(account.checkSelf());
    return super.checkSelf();
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
