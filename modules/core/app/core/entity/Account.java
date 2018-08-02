package core.entity;

import core.BaseModel;
import io.ebean.annotation.EnumValue;
import io.ebean.annotation.Index;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import static com.google.common.base.Strings.isNullOrEmpty;
import static core.exception.ApplicationException.badRequest;

/**
 * @author qiang.zhang
 */
@Entity
@Table(name = "accounts")
public final class Account extends BaseModel {
  @Index(name = "index_account_username")
  @Column(unique = true, nullable = false, columnDefinition = "用户名/帐号", length = 16)
  public String username;
  @Column(nullable = false, columnDefinition = "用户密码，Base 64 加密", length = 16)
  public String password;
  @Column(nullable = false, columnDefinition = "权限等级：游客、用户、管理员、创始人")
  public Level level;

  @OneToOne(optional = false)
  public User user;

  @Override public void check() {
    badRequest(!isNullOrEmpty(username) && username.length() <= 16,
        "Invalid username: " + username);
    badRequest(!isNullOrEmpty(password) && password.length() <= 16,
        "Invalid password: " + password);
    badRequest(level != null, "Null level");
    badRequest(user != null, "Null user");
  }

  @Override public int hashCode() {
    return Objects.hash(super.hashCode(), username, password, level, username);
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
      Objects.requireNonNull(value, "value");
      switch (value.toUpperCase()) {
        case "游客":
        case "GUEST":
          return GUEST;
        case "用户":
        case "USER":
          return USER;
        case "管理员":
        case "ADMIN":
          return ADMIN;
        case "创始人":
        case "AUTHOR":
          return AUTHOR;
        default:
          return GUEST;
      }
    }
  }
}
