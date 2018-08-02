package core.entity;

import com.google.common.base.Strings;
import core.BaseModel;
import io.ebean.annotation.Index;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import util.LinkHelper;

import static core.exception.ApplicationException.badRequest;

/**
 * 用户。
 *
 * @author qiang.zhang
 */
@Entity
@Table(name = "users")
public final class User extends BaseModel {
  @Index(name = "index_user_nickname")
  @Column(unique = true, nullable = false, length = 16, columnDefinition = "昵称，唯一，非 null，最长 16 个字符。")
  public String nickname;
  @Column(nullable = false, columnDefinition = "头像链接，非 null。")
  public String avatar;

  @Override public void check() {
    badRequest(checkNickName(), "Invalid nickname: " + nickname);
    badRequest(LinkHelper.simpleCheck(avatar), "Invalid avatar: " + avatar);
  }

  private boolean checkNickName() {
    return !Strings.isNullOrEmpty(nickname) && nickname.length() <= 16;
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
