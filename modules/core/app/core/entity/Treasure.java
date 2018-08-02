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
  @Column(unique = true, nullable = false, length = 12)
  public String name;
  @Column(unique = true, nullable = false)
  public String link;

  @Override public void check() {
    badRequest(!Strings.isNullOrEmpty(name) && name.length() <= 12, "Invalid name: " + name);
    badRequest(LinkHelper.simpleCheck(link), "Invalid link: " + link);
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
