package core.entity;

import com.google.common.base.Preconditions;
import core.util.Accounts;
import io.ebean.annotation.Index;
import java.util.Base64;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
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

  @Index(name = BASE_INDEX + COL_USERNAME)
  @Column(name = COL_USERNAME, unique = true, nullable = false, length = 16)
  public String username;
  @Column(name = COL_PASSWORD, nullable = false, length = 16)
  public String password;

  @OneToOne
  public User user;
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "account_id")
  public Set<Treasure> treasures;

  @Override public boolean checkSelf() {
    Preconditions.checkState(Accounts.checkUsername(username));
    String value = new String(Base64.getDecoder().decode(password));
    Preconditions.checkState(Accounts.checkPassword(value));
    if (user != null) {
      user.checkSelf();
    }
    if (treasures != null) {
      treasures.forEach(Treasure::checkSelf);
    }
    return true;
  }

  @Override public int hashCode() {
    return Objects.hash(super.hashCode(), username, password, user, treasures);
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
        && Objects.equals(user, other.user)
        && Objects.equals(treasures, other.treasures);
  }

  @Override public String toString() {
    return toStringHelper()
        .add("用户账号", username)
        .add("用户密码", password)
        .add("用户资料", user)
        .add("用户宝藏", treasures)
        .toString();
  }
}
