package core.entity;

import com.google.common.base.Preconditions;
import core.EBeanModel;
import io.ebean.annotation.Index;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 用户。
 * <p>
 * 持久化昵称、头像。
 * <p>
 * 这就是一个用户信息表。
 *
 * @author qiang.zhang
 */
@Entity
@Table(name = "users")
public final class User extends EBeanModel {
  @Index(name = "index_user_nickname")
  @Column(unique = true, nullable = false, length = 16, columnDefinition = "昵称，唯一，非空，最长 16 个字符。")
  public String nickname;
  @Column(nullable = false, columnDefinition = "头像，非空，不限长度。")
  public String avatar;

  @Override public boolean checkSelf() {
    Objects.requireNonNull(nickname);
    Objects.requireNonNull(avatar);
    Preconditions.checkState(nickname.length() <= 16);
    return super.checkSelf();
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
