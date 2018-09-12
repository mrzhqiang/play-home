package core.entity;

import com.google.common.base.Preconditions;
import core.util.Users;
import io.ebean.annotation.Index;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;
import util.LinkHelper;

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
  public Profiles profiles;

  @Override public boolean checkSelf() {
    Preconditions.checkState(Users.checkNickname(nickname));
    if (avatar != null) {
      Preconditions.checkState(LinkHelper.simpleCheck(avatar));
    }
    if (profiles != null) {
      Preconditions.checkState(profiles.checkSelf());
    }
    return true;
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
    return toStringHelper()
        .add("昵称", nickname)
        .add("头像", avatar)
        .add("资料", profiles)
        .toString();
  }
}
