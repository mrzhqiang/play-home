package core.entity;

import com.google.common.base.Strings;
import core.BaseModel;
import io.ebean.annotation.Index;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import util.LinkHelper;

import static core.exception.ApplicationException.*;

/**
 * 宝藏。
 *
 * @author mrzhqiang
 */
@Entity
@Table(name = "treasures")
public final class Treasure extends BaseModel {
  @Index(name = "index_treasure_name")
  @Column(unique = true, nullable = false, length = 12, columnDefinition = "宝藏名，唯一，非 null，最长 12 个字符。")
  public String name;
  @Column(unique = true, nullable = false, columnDefinition = "宝藏链接，唯一，非 null。")
  public String link;

  @Override public void check() {
    badRequest(checkName(), "Invalid name: " + name);
    badRequest(LinkHelper.simpleCheck(link), "Invalid link: " + link);
  }

  private boolean checkName() {
    return !Strings.isNullOrEmpty(name) && name.length() >= 2 && name.length() <= 12;
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
        .add("name", name)
        .add("link", link)
        .toString();
  }
}
