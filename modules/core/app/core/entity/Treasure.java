package core.entity;

import com.google.common.base.Preconditions;
import core.EBeanModel;
import io.ebean.annotation.Index;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 宝藏。
 * <p>
 * 包含：宝藏名字和宝藏链接。
 * <p>
 * 实际上就是用户的网站收藏夹。
 *
 * @author mrzhqiang
 */
@Entity
@Table(name = "treasures")
public final class Treasure extends EBeanModel {
  public static final String NAME = "name";
  public static final String LINK = "link";

  @Index(name = "index_treasure_" + NAME)
  @Column(name = NAME, unique = true, nullable = false, length = 12,
      columnDefinition = "宝藏名，唯一，非空，最长 12 个字符。")
  public String name;
  @Column(name = LINK, unique = true, nullable = false,
      columnDefinition = "宝藏链接，唯一，非空。")
  public String link;

  @Override public boolean checkSelf() {
    Preconditions.checkNotNull(name);
    Preconditions.checkNotNull(link);
    Preconditions.checkState(name.length() <= 12);
    return super.checkSelf();
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
