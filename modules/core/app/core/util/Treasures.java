package core.util;

import core.entity.Treasure;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * 宝藏工具。
 *
 * @author mrzhqiang
 */
public final class Treasures {
  private Treasures() {
    throw new AssertionError("No instance.");
  }

  /**
   * 检查宝藏名字。
   */
  public static boolean checkName(@Nullable String value) {
    return value != null && !value.trim().isEmpty() && value.length() <= 12;
  }

  /**
   * 宝藏。
   */
  @Nonnull
  public static Treasure of(@Nonnull String name, @Nonnull String link) {
    Treasure treasure = new Treasure();
    treasure.name = name;
    treasure.link = link;
    return treasure;
  }
}
