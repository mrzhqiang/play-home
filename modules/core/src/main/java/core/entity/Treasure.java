package core.entity;

import com.google.common.base.Verify;
import io.ebean.annotation.Index;
import io.ebean.annotation.Length;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import util.NameHelper;

/**
 * 宝藏。
 * <p>
 * 这意味着是私人收藏。
 *
 * @author mrzhqiang
 */
@Entity
@Table(name = "treasures")
public final class Treasure extends EBeanModel {
  public static final String COL_NAME = "name";

  @Index(name = "index_treasure_name")
  @Column(name = COL_NAME, nullable = false)
  @Length(24)
  private String name;

  // TODO 游戏（包含：服务器信息，游戏角色，职业，等等）

  @Nonnull
  public String getName() {
    return name;
  }

  public void setName(@Nonnull String name) {
    boolean b = NameHelper.checkName(name, 2, 24);
    Verify.verify(b, "invalid name: %s", name);
    this.name = name;
  }

  @Override public int hashCode() {
    return Objects.hash(super.hashCode(), name);
  }

  @Override public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (!(obj instanceof Treasure)) {
      return false;
    }

    Treasure other = (Treasure) obj;
    return super.equals(obj)
        && Objects.equals(name, other.name);
  }

  @Override public String toString() {
    return stringHelper()
        .add("宝藏名", name)
        .toString();
  }

  /**
   * 新的宝藏。
   */
  public static Treasure of(@Nonnull String name) {
    Treasure treasure = new Treasure();
    treasure.setName(name);
    return treasure;
  }
}
