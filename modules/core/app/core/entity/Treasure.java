package core.entity;

import io.ebean.Finder;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 宝藏。
 *
 * @author mrzhqiang
 */
@Entity
@Table(name = "treasures")
public final class Treasure extends BasicModel {
  public static final Finder<Long, Treasure> find = new Finder<>(Treasure.class);

  @Column(unique = true, nullable = false, length = 12)
  public String name;
  @Column(unique = true, nullable = false)
  public String link;
  public String description;

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
        .add("description", description)
        .add("link", link)
        .toString();
  }
}
