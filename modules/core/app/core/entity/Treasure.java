package core.entity;

import com.google.common.base.Preconditions;
import core.util.Treasures;
import io.ebean.annotation.Index;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import util.LinkHelper;

/**
 * 宝藏。
 *
 * @author mrzhqiang
 */
@Entity
@Table(name = "treasures")
public final class Treasure extends EBeanModel {
  private static final String BASE_INDEX = "index_treasure_";

  public static final String COL_NAME = "name";
  public static final String COL_LINK = "link";

  @Index(name = BASE_INDEX + COL_NAME)
  @Column(name = COL_NAME, unique = true, nullable = false, length = 12)
  public String name;
  @Column(name = COL_LINK, unique = true, nullable = false)
  public String link;

  @Override public boolean checkSelf() {
    Preconditions.checkNotNull(name);
    Preconditions.checkState(Treasures.checkName(name));
    Preconditions.checkNotNull(link);
    Preconditions.checkState(LinkHelper.simpleCheck(link));
    return true;
  }

  @Override public int hashCode() {
    return Objects.hash(super.hashCode(), name, link);
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
        && Objects.equals(name, other.name)
        && Objects.equals(link, other.link);
  }

  @Override public String toString() {
    return toStringHelper()
        .add("宝藏", name)
        .add("地址", link)
        .toString();
  }
}
