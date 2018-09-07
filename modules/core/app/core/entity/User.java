package core.entity;

import com.google.common.base.Preconditions;
import io.ebean.annotation.Index;
import java.util.Objects;
import javax.persistence.Column;
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

  public static final String INDEX_NICKNAME = BASE_INDEX + COL_NICKNAME;

  @Index(name = INDEX_NICKNAME)
  @Column(name = COL_NICKNAME, unique = true, nullable = false, length = 16)
  public String nickname;
  @Column(name = COL_AVATAR, nullable = false)
  public String avatar;

  @Override public boolean checkSelf() {
    Preconditions.checkNotNull(nickname);
    Preconditions.checkState(!nickname.isEmpty() && nickname.length() <= 16);
    Preconditions.checkNotNull(avatar);
    Preconditions.checkState(LinkHelper.simpleCheck(avatar));
    return true;
  }

  @Override public int hashCode() {
    return Objects.hash(super.hashCode(), nickname, avatar);
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
        && Objects.equals(avatar, other.avatar);
  }

  @Override public String toString() {
    return toStringHelper()
        .add("昵称", nickname)
        .add("头像", avatar)
        .toString();
  }
}
