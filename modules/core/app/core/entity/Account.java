package core.entity;

import com.google.common.base.Preconditions;
import core.EBeanModel;
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
 * <p>
 * 包含：用户名、密码、等级、上次登录时间、上次登录设备。
 * <p>
 * 等级：区分游客、用户、管理员、创始人。
 * <p>
 * 对应权限：游客浏览，用户发帖，管理员封印，创始人增减。
 * <p>
 * 上次登录时间、上次登录设备：这两个字段模仿的是 Linux 服务器 SSH 登录成功后的欢迎词。
 * <p>
 * 通常来说，只有登录才能拿到唯一的令牌；重复登录将替换令牌，并通知上一个设备重新登录。
 *
 * @author qiang.zhang
 */
@Entity
@Table(name = "accounts")
public final class Account extends EBeanModel {
  @Index(name = "index_account_username")
  @Column(unique = true, nullable = false, length = 16, columnDefinition = "用户名，唯一，非空，最长 16 个字符。")
  public String username;
  @Column(nullable = false, length = 16, columnDefinition = "密码，Base64 加密，非空，最长 16 个字符。")
  public String password;
  @Column(nullable = false, columnDefinition = "权限等级：游客、用户、管理员、创始人，非空，枚举类型。")
  public Level level;
  @Column(name = "last_login_time", nullable = false, columnDefinition = "上次登录时间，非空。")
  public Instant lastLoginTime;
  @Column(name = "last_login_device", nullable = false, columnDefinition = "上次登录设备，非空。")
  public String lastLoginDevice;

  @OneToOne(optional = false)
  public Token token;

  @Override public boolean checkSelf() {
    Preconditions.checkNotNull(username);
    Preconditions.checkNotNull(password);
    Preconditions.checkNotNull(level);
    Preconditions.checkNotNull(lastLoginTime);
    Preconditions.checkNotNull(lastLoginDevice);
    Preconditions.checkNotNull(token);
    Preconditions.checkState(username.length() <= 16);
    Preconditions.checkState(password.length() <= 16);
    Preconditions.checkState(token.checkSelf());
    return super.checkSelf();
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
