package core.util;

import core.entity.User;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import util.RandomHelper;

/**
 * 用户工具。
 *
 * @author mrzhqiang
 */
public final class Users {
  private Users() {
    throw new AssertionError("No instance.");
  }

  /**
   * 检查昵称。
   */
  public static boolean checkNickname(@Nullable String value) {
    return value != null && !value.isEmpty() && value.length() <= 24;
  }

  /**
   * 匿名用户。
   */
  @Nonnull
  public static User ofAnonymity() {
    String nickname = "用户_" + RandomHelper.ofNumber(11);
    String avatar = "";
    return of(nickname, avatar);
  }

  /**
   * 正常用户。
   */
  @Nonnull
  public static User of(@Nonnull String nickname, @Nonnull String avatar) {
    User user = new User();
    user.nickname = nickname;
    user.avatar = avatar;
    return user;
  }
}
