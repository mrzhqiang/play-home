package core.entity;

import core.BaseModel;
import io.ebean.annotation.EnumValue;
import io.ebean.annotation.Index;
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
public final class Account extends BaseModel {
  @Index(name = "index_account_username")
  @Column(unique = true, nullable = false, length = 16, columnDefinition = "用户名，唯一，非 null，最长 16 个字符。")
  public String username;
  @Column(nullable = false, length = 16, columnDefinition = "密码，Base64 加密，非 null，最长 16 个字符。")
  public String password;
  @Column(nullable = false, columnDefinition = "权限等级：游客、用户、管理员、创始人，非 null。")
  public Level level;

  @OneToOne(optional = false)
  public User user;

  public enum Level {
    @EnumValue("GUEST")
    GUEST,
    @EnumValue("USER")
    USER,
    @EnumValue("ADMIN")
    ADMIN,
    @EnumValue("AUTHOR")
    AUTHOR,;
  }

  public static Level levelOf(String value) {
    Objects.requireNonNull(value);
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

  @Override public int hashCode() {
    return Objects.hash(super.hashCode(), username, password, level, username);
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
        && Objects.equals(user, other.user);
  }

  @Override public String toString() {
    return toStringHelper()
        .add("username", username)
        .add("password", password)
        .add("level", level)
        .add("user", user)
        .toString();
  }
}
