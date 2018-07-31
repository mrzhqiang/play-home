package core.entity;

import com.google.common.base.Strings;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import util.LinkHelper;

import static core.ApplicationException.*;

/**
 * 宝藏实体。
 *
 * @author mrzhqiang
 */
@Entity
@Table(name = "treasures")
public final class Treasure extends BasicModel {
  @Column(unique = true, nullable = false, length = 12)
  public String name;
  @Column(unique = true, nullable = false)
  public String link;
  public String description;

  @Override public BasicModel check() {
    badRequest(!Strings.isNullOrEmpty(name) && name.length() <= 12, "Invalid name: " + name);
    badRequest(LinkHelper.simpleCheck(link), "Invalid link: " + link);
    return this;
  }

  @Override public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (!(obj instanceof Treasure)) {
      return false;
    }

    Treasure other = (Treasure) obj;
    return super.equals(other);
  }

  @Override public String toString() {
    return toStringHelper()
        .add("name", name)
        .add("link", link)
        .add("description", description)
        .toString();
  }
}
