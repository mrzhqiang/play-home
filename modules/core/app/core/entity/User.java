package core.entity;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import core.EBeanModel;
import io.ebean.annotation.Index;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 用户。
 * <p>
 * 包含：昵称、头像、宝藏列表。
 * <p>
 * 通过用户可以拿到所收藏的宝藏列表；若是查看他人资料，则比较互相收藏的宝藏，进而知晓彼此兴趣爱好。
 *
 * @author qiang.zhang
 */
@Entity
@Table(name = "users")
public final class User extends EBeanModel {
  public static final String NICKNAME = "nickname";
  public static final String AVATAR = "avatar";

  @Index(name = "index_user_nickname")
  @Column(name = NICKNAME, unique = true, nullable = false, length = 16,
      columnDefinition = "昵称，唯一，非空，最长 16 个字符。")
  public String nickname;
  @Column(name = AVATAR, nullable = false,
      columnDefinition = "头像，非空，不限长度。")
  public String avatar;

  @OneToMany()
  public Set<Treasure> treasures = Sets.newHashSet();

  @Override public boolean checkSelf() {
    Preconditions.checkNotNull(nickname);
    Preconditions.checkNotNull(avatar);
    Preconditions.checkState(nickname.length() <= 16);
    Preconditions.checkNotNull(treasures);
    Preconditions.checkState(
        !treasures.isEmpty() && treasures.stream().allMatch(Treasure::checkSelf));
    return super.checkSelf();
  }

  @Override public int hashCode() {
    return Objects.hash(super.hashCode(), nickname, avatar, treasures);
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
        && Objects.equals(treasures, other.treasures);
  }

  @Override public String toString() {
    return toStringHelper()
        .add("昵称", nickname)
        .add("头像", avatar)
        .add("宝藏列表", treasures)
        .toString();
  }
}
