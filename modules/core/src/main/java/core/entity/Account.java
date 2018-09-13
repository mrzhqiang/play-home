package core.entity;

import com.google.common.base.Verify;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import io.ebean.annotation.Index;
import java.util.Base64;
import java.util.Objects;
import java.util.regex.Pattern;
import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import util.RandomHelper;

/**
 * 帐号。
 * <p>
 * TODO 积分、等级、荣耀，等等。
 *
 * @author qiang.zhang
 */
@Entity
@Table(name = "accounts")
public final class Account extends EBeanModel {
  private static final String REGEX_USERNAME = "[a-zA-Z0-9._~+/-]{6,16}";
  private static final String REGEX_PASSWORD = "[a-z0-9]{6,16}";

  private static final String BASE_INDEX = "index_account_";

  public static final String COL_USERNAME = "username";
  public static final String COL_PASSWORD = "password";

  @Index(name = BASE_INDEX + COL_USERNAME)
  @Column(name = COL_USERNAME, unique = true, nullable = false, length = 16)
  private String username;
  @Column(name = COL_PASSWORD, nullable = false, length = 16)
  private String password;

  @OneToOne
  private User user;

  @Nonnull
  public String getUsername() {
    return username;
  }

  public void setUsername(@Nonnull String username) {
    Verify.verify(!username.isEmpty() && Pattern.matches(REGEX_USERNAME, username),
        "invalid username: %s", username);
    this.username = username;
  }

  @Nonnull
  public String getPassword() {
    return new String(Base64.getDecoder().decode(password));
  }

  public void setPassword(@Nonnull String password) {
    Verify.verify(!password.isEmpty() && Pattern.matches(REGEX_PASSWORD, password),
        "invalid password: %s", password);
    this.password = Base64.getEncoder().encodeToString(password.getBytes());
  }

  @CheckForNull
  public User getUser() {
    return user;
  }

  public void setUser(@Nonnull User user) {
    this.user = user;
  }

  @Override public int hashCode() {
    return Objects.hash(super.hashCode(), username, password);
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
        && Objects.equals(password, other.password);
  }

  @Override public String toString() {
    return stringHelper()
        .add("账号", username)
        .add("密码", password)
        .toString();
  }

  /**
   * 游客账号。
   */
  @Nonnull
  @CanIgnoreReturnValue
  public static Account ofGuest() {
    return of(RandomHelper.ofString(16), RandomHelper.ofNumber(6));
  }

  /**
   * 用户账号。
   */
  @Nonnull
  @CanIgnoreReturnValue
  public static Account of(@Nonnull String username, @Nonnull String password) {
    Account account = new Account();
    account.setUsername(username);
    account.setPassword(password);
    return account;
  }
}
