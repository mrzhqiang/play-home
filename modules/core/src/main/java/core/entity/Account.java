package core.entity;

import com.google.common.base.Verify;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import io.ebean.annotation.Index;
import io.ebean.annotation.Length;
import java.util.Base64;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import util.NameHelper;

import static util.RandomHelper.ofNumber;
import static util.RandomHelper.ofString;

/**
 * 帐号。
 * <p>
 * TODO mobile email open_id qq etc.
 *
 * @author qiang.zhang
 */
@Entity
@Table(name = "accounts")
public final class Account extends EBeanModel {
  public static final String REGEX_USERNAME = "[a-zA-Z0-9._~+/-]";
  public static final String REGEX_PASSWORD = "[a-z0-9]";

  public static final String COL_USERNAME = "username";
  public static final String COL_PASSWORD = "password";

  @Index(name = "index_account_username")
  @Column(name = COL_USERNAME, unique = true, nullable = false)
  @Length(16)
  private String username;
  @Column(name = COL_PASSWORD, nullable = false)
  @Length(16)
  private String password;

  @OneToOne(optional = false)
  private User user;

  @Nonnull
  public User getUser() {
    return user;
  }

  public void setUsername(@Nonnull String username) {
    boolean b = NameHelper.checkRegexAndLength(REGEX_USERNAME, username, 6, 16);
    Verify.verify(b, "invalid username: %s", username);
    this.username = username;
  }

  public void setPassword(@Nonnull String password) {
    boolean b = NameHelper.checkRegexAndLength(REGEX_PASSWORD, password, 6, 16);
    Verify.verify(b, "invalid password: %s", password);
    this.password = Base64.getEncoder().encodeToString(password.getBytes());
  }

  public void setUser(@Nonnull User user) {
    this.user = user;
  }

  @Override public int hashCode() {
    return Objects.hash(super.hashCode(), username, password, user);
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
        && Objects.equals(user, other.user);
  }

  @Override public String toString() {
    return stringHelper()
        .add("账号", username)
        .add("密码", password)
        .add("用户", user)
        .toString();
  }

  /**
   * 游客，匿名用户。
   */
  @Nonnull
  @CanIgnoreReturnValue
  public static Account ofGuest() {
    return of(ofString(16), ofNumber(6), User.ofAnonymity());
  }

  /**
   * 普通，正常用户。
   */
  @Nonnull
  @CanIgnoreReturnValue
  public static Account of(@Nonnull String username, @Nonnull String password, @Nonnull User user) {
    Account account = new Account();
    account.setUsername(username);
    account.setPassword(password);
    account.setUser(user);
    return account;
  }
}
