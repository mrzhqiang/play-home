package core.util;

import com.google.common.base.MoreObjects;
import core.entity.Treasure;
import java.time.Instant;
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
    return value != null && !value.isEmpty() && value.length() <= 12;
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

  /**
   * 合并新旧实体。
   */
  public static Treasure merge(@Nonnull Treasure oldValue, @Nonnull Treasure newValue) {
    oldValue.name = MoreObjects.firstNonNull(newValue.name, oldValue.name);
    oldValue.link = MoreObjects.firstNonNull(newValue.link, oldValue.link);
    oldValue.modified = Instant.now();
    return oldValue;
  }
}
