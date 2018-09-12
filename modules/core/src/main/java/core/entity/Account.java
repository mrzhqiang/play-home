package core.entity;

import com.google.common.base.Preconditions;
import core.util.Accounts;
import io.ebean.annotation.EnumValue;
import io.ebean.annotation.Index;
import java.time.Instant;
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
  public static final String COL_LEVEL = "level";
  public static final String COL_LAST_TIME = "last_time";
  public static final String COL_LAST_DEVICE = "last_device";

  public static final String DEFAULT_DEVICE_UNKNOWN = "Unknown";

  @Index(name = BASE_INDEX + COL_USERNAME)
  @Column(name = COL_USERNAME, unique = true, nullable = false, length = 16)
  public String username;
  @Column(name = COL_PASSWORD, nullable = false, length = 16)
  public String password;
  @Column(name = COL_LEVEL)
  public Level level;
  @Column(name = COL_LAST_TIME)
  public Instant lastTime;
  @Column(name = COL_LAST_DEVICE)
  public String lastDevice;

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
    return Objects.hash(super.hashCode(),
        username, password, level, lastTime, lastDevice, user, treasures);
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
        && Objects.equals(lastTime, other.lastTime)
        && Objects.equals(lastDevice, other.lastDevice)
        && Objects.equals(user, other.user)
        && Objects.equals(treasures, other.treasures);
  }

  @Override public String toString() {
    return toStringHelper()
        .add("用户名", username)
        .add("密码", password)
        .add("权限等级", level)
        .add("上次登录时间", lastTime)
        .add("上次登录设备", lastDevice)
        .add("用户资料", user)
        .add("宝藏列表", treasures)
        .toString();
  }

  public enum Level {
    @EnumValue("GUEST")
    GUEST("游客"),
    @EnumValue("USER")
    USER("用户"),
    @EnumValue("ADMIN")
    ADMIN("管理员"),
    @EnumValue("AUTHOR")
    AUTHOR("创始人"),;

    final String name;

    Level(String value) {
      this.name = value;
    }

    public static Level of(String value) {
      Preconditions.checkNotNull(value);
      for (Level level : Level.values()) {
        if (level.name.equals(value)) {
          return level;
        }
      }

      return Level.valueOf(value.toUpperCase());
    }

    @Override public String toString() {
      return name;
    }
  }
}
