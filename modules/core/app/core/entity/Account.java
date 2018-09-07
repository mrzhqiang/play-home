package core.entity;

import com.google.common.base.Preconditions;
import io.ebean.annotation.EnumValue;
import io.ebean.annotation.Index;
import java.time.Instant;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * 帐号。
 *
 * @author qiang.zhang
 */
@Entity
@Table(name = "accounts")
public final class Account extends EBeanModel {
  private static final String BASE_INDEX = "index_account_";

  public static final String COL_USERNAME = "username";
  public static final String COL_PASSWORD = "password";
  public static final String COL_LEVEL = "level";
  public static final String COL_LAST_LOGIN_TIME = "last_login_time";
  public static final String COL_LAST_LOGIN_DEVICE = "last_login_device";

  public static final String INDEX_USERNAME = BASE_INDEX + COL_USERNAME;

  public static final String DEFAULT_DEVICE_UNKNOWN = "Unknown";

  @Index(name = INDEX_USERNAME)
  @Column(name = COL_USERNAME, unique = true, nullable = false, length = 16)
  public String username;
  @Column(name = COL_PASSWORD, nullable = false, length = 16)
  public String password;
  @Column(name = COL_LEVEL, nullable = false)
  public Level level;
  @Column(name = COL_LAST_LOGIN_TIME, nullable = false)
  public Instant lastLoginTime;
  @Column(name = COL_LAST_LOGIN_DEVICE, nullable = false)
  public String lastLoginDevice;

  @OneToOne
  public Token token;

  @Override public boolean checkSelf() {
    Preconditions.checkNotNull(username);
    Preconditions.checkState(username.length() > 5 && username.length() <= 16);
    Preconditions.checkNotNull(password);
    Preconditions.checkState(password.length() > 5 && password.length() <= 16);
    Preconditions.checkNotNull(level);
    Preconditions.checkNotNull(lastLoginTime);
    Preconditions.checkNotNull(lastLoginDevice);
    if (token != null) {
      Preconditions.checkState(token.checkSelf());
    }
    return true;
  }

  @Override public int hashCode() {
    return Objects.hash(super.hashCode(),
        username, password, level, lastLoginTime, lastLoginDevice, token);
  }

  @Override public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (!(obj instanceof Account)) {
      return false;
    }

    Account other = (Account) obj;
    return super.equals(obj)
        && Objects.equals(username, other.username)
        && Objects.equals(password, other.password)
        && Objects.equals(level, other.level)
        && Objects.equals(lastLoginTime, other.lastLoginTime)
        && Objects.equals(lastLoginDevice, other.lastLoginDevice)
        && Objects.equals(token, other.token);
  }

  @Override public String toString() {
    return toStringHelper()
        .add("用户名", username)
        .add("密码", password)
        .add("权限等级", level)
        .add("上次登录时间", lastLoginTime)
        .add("上次登录设备", lastLoginDevice)
        .add("令牌", token)
        .toString();
  }

  public enum Level {
    @EnumValue("GUEST")
    GUEST,
    @EnumValue("USER")
    USER,
    @EnumValue("ADMIN")
    ADMIN,
    @EnumValue("AUTHOR")
    AUTHOR,;

    public static Level of(String value) {
      Preconditions.checkNotNull(value);
      switch (value.toUpperCase()) {
        case "游客":
        case "GUEST":
          return Level.GUEST;
        case "用户":
        case "USER":
          return Level.USER;
        case "管理员":
        case "ADMIN":
          return Level.ADMIN;
        case "创始人":
        case "AUTHOR":
          return Level.AUTHOR;
        default:
          return Level.GUEST;
      }
    }
  }
}
