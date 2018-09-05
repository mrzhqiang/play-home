package core.entity;

import com.google.common.base.Preconditions;
import core.EBeanModel;
import io.ebean.annotation.Index;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * 令牌。
 * <p>
 * 包含：访问令牌、刷新令牌和过期时间。
 * <p>
 * 通过此令牌可以访问相应的用户资料。
 *
 * @author mrzhqiang
 */
@Entity
@Table(name = "tokens")
public final class Token extends EBeanModel {
  public static final String ACCESS_TOKEN = "access_token";
  public static final String REFRESH_TOKEN = "refresh_token";
  public static final String EXPIRES_IN = "expires_in";

  @Index(name = "index_token_" + ACCESS_TOKEN)
  @Column(name = ACCESS_TOKEN, unique = true, nullable = false,
      columnDefinition = "访问令牌，唯一，非空。")
  public String accessToken;
  @Column(name = REFRESH_TOKEN, nullable = false,
      columnDefinition = "刷新令牌，非空。")
  public String refreshToken;
  @Column(name = EXPIRES_IN, nullable = false,
      columnDefinition = "过期时间，非空，单位秒。")
  public Long expiresIn;

  @OneToOne(optional = false)
  public User user;

  @Override public boolean checkSelf() {
    Preconditions.checkNotNull(accessToken);
    Preconditions.checkNotNull(refreshToken);
    Preconditions.checkNotNull(expiresIn);
    Preconditions.checkNotNull(user);
    Preconditions.checkState(user.checkSelf());
    return super.checkSelf();
  }

  @Override public int hashCode() {
    return Objects.hash(super.hashCode(), accessToken, refreshToken, expiresIn, user);
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
        && Objects.equals(user, other.user);
  }

  @Override public String toString() {
    return toStringHelper()
        .add("访问令牌", accessToken)
        .add("刷新令牌", refreshToken)
        .add("过期期限", expiresIn)
        .add("用户资料", user)
        .toString();
  }
}
