package core.entity;

import com.google.common.base.Verify;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import io.ebean.annotation.EnumValue;
import io.ebean.annotation.Index;
import io.ebean.annotation.Length;
import java.util.Objects;
import java.util.Set;
import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import util.LinkHelper;
import util.NameHelper;
import util.RandomHelper;

/**
 * 用户。
 *
 * @author qiang.zhang
 */
@Entity
@Table(name = "users")
public final class User extends EBeanModel {
  public static final String COL_NICKNAME = "nickname";
  public static final String COL_AVATAR = "avatar";
  public static final String COL_LEVEL = "level";

  @Index(name = "index_user_nickname")
  @Column(name = COL_NICKNAME, unique = true, nullable = false)
  @Length(24)
  private String nickname;
  @Column(name = COL_LEVEL, nullable = false)
  private Level level;
  @Column(name = COL_AVATAR)
  private String avatar;
  @Embedded
  private Profiles profiles;
  @OneToOne
  private Client client;
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "user_id")
  private Set<Treasure> treasures;

  @Nonnull
  public String getNickname() {
    return nickname;
  }

  @Nonnull
  public Level getLevel() {
    return level;
  }

  @CheckForNull
  public String getAvatar() {
    return avatar;
  }

  @CheckForNull
  public Profiles getProfiles() {
    return profiles;
  }

  @CheckForNull
  public Client getClient() {
    return client;
  }

  @CheckForNull
  public Set<Treasure> getTreasures() {
    return treasures;
  }

  public void setNickname(@Nonnull String nickname) {
    boolean b = NameHelper.checkName(nickname, 1, 24);
    Verify.verify(b, "invalid nickname: %s");
    this.nickname = nickname;
  }

  public void setLevel(@Nonnull Level level) {
    this.level = level;
  }

  public void setAvatar(@Nullable String avatar) {
    if (avatar != null) {
      boolean b = LinkHelper.simpleCheck(avatar);
      Verify.verify(b, "invalid avatar: %s", avatar);
    }
    this.avatar = avatar;
  }

  public void setProfiles(@Nullable Profiles profiles) {
    this.profiles = profiles;
  }

  public void setClient(@Nullable Client client) {
    this.client = client;
  }

  public void setTreasures(@Nullable Set<Treasure> treasures) {
    this.treasures = treasures;
  }

  @Override public int hashCode() {
    return Objects.hash(super.hashCode(), nickname, level, avatar, client, profiles, treasures);
  }

  @Override public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (!(obj instanceof User)) {
      return false;
    }

    User other = (User) obj;
    return super.equals(obj)
        && Objects.equals(nickname, other.nickname)
        && Objects.equals(level, other.level)
        && Objects.equals(avatar, other.avatar)
        && Objects.equals(client, other.client)
        && Objects.equals(profiles, other.profiles)
        && Objects.equals(treasures, other.treasures);
  }

  @Override public String toString() {
    return stringHelper()
        .add("昵称", nickname)
        .add("权限", level)
        .add("头像", avatar)
        .add("设备", client)
        .add("资料", profiles)
        .add("宝藏", treasures)
        .toString();
  }

  /**
   * 匿名用户。
   */
  @Nonnull
  @CanIgnoreReturnValue
  public static User ofAnonymity() {
    User user = new User();
    user.setNickname(RandomHelper.ofNumber(11));
    user.setLevel(Level.GUEST);
    return user;
  }

  /**
   * 正常用户。
   */
  @Nonnull
  @CanIgnoreReturnValue
  public static User of(@Nonnull String nickname, @Nullable String avatar,
      @Nullable Client client, @Nullable Profiles profiles, @Nullable Set<Treasure> treasures) {
    User user = new User();
    user.setNickname(nickname);
    user.setLevel(Level.USER);
    user.setAvatar(avatar);
    user.setProfiles(profiles);
    user.setClient(client);
    user.setTreasures(treasures);
    return user;
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

    public static Level of(@Nonnull String value) {
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
