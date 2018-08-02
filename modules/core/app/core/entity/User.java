package core.entity;

import core.BaseModel;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import util.LinkHelper;

import static com.google.common.base.Strings.isNullOrEmpty;
import static core.exception.ApplicationException.badRequest;

/**
 * 用户。
 *
 * @author qiang.zhang
 */
@Entity
@Table(name = "users")
public final class User extends BaseModel {
  @Column(nullable = false, length = 16)
  public String nickname;
  @Column(nullable = false)
  public String avatar;

  @Override public void check() {
    badRequest(!isNullOrEmpty(nickname) && nickname.length() <= 16,
        "Invalid nickname: " + nickname);
    badRequest(LinkHelper.simpleCheck(avatar), "Invalid avatar: " + avatar);
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
        .add("nickname", nickname)
        .add("avatar", avatar)
        .toString();
  }
}
