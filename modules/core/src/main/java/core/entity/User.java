package core.entity;

import com.google.common.base.Verify;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import io.ebean.annotation.Index;
import java.util.Objects;
import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;
import util.RandomHelper;

/**
 * 用户。
 *
 * @author qiang.zhang
 */
@Entity
@Table(name = "users")
public final class User extends EBeanModel {
  private static final String BASE_INDEX = "index_user_";

  public static final String COL_NICKNAME = "nickname";
  public static final String COL_AVATAR = "avatar";

  @Index(name = BASE_INDEX + COL_NICKNAME)
  @Column(name = COL_NICKNAME, nullable = false, length = 24)
  public String nickname;
  @Column(name = COL_AVATAR)
  public String avatar;

  @Embedded
  private Profiles profiles;

  @Nonnull
  public String getNickname() {
    return nickname;
  }

  public void setNickname(@Nonnull String nickname) {
    Verify.verify(!nickname.isEmpty() && nickname.length() <= 24,
        "invalid nickname: %s", nickname);
    this.nickname = nickname;
  }

  @CheckForNull
  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(@Nonnull String avatar) {
    this.avatar = avatar;
  }

  @CheckForNull
  public Profiles getProfiles() {
    return profiles;
  }

  public void setProfiles(@Nonnull Profiles profiles) {
    this.profiles = profiles;
  }

  @Override public int hashCode() {
    return Objects.hash(super.hashCode(), nickname, avatar, profiles);
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
        && Objects.equals(avatar, other.avatar)
        && Objects.equals(profiles, other.profiles);
  }

  @Override public String toString() {
    return stringHelper()
        .add("昵称", nickname)
        .add("头像", avatar)
        .add("资料", profiles)
        .toString();
  }

  /**
   * 匿名用户。
   */
  @Nonnull
  @CanIgnoreReturnValue
  public static User ofAnonymity() {
    return of("用户_" + RandomHelper.ofNumber(11), "");
  }

  /**
   * 正常用户。
   */
  @Nonnull
  @CanIgnoreReturnValue
  public static User of(@Nonnull String nickname, @Nonnull String avatar) {
    User user = new User();
    user.setNickname(nickname);
    user.setAvatar(avatar);
    return user;
  }
}
