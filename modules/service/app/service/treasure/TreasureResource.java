package service.treasure;

import com.google.common.base.MoreObjects;
import java.util.Objects;

/**
 * @author mrzhqiang
 */
public final class TreasureResource {
  public String id;
  public String name;
  public String description;
  public String link;
  public String href;

  public TreasureResource() {
  }

  public TreasureResource(String id, String name, String description, String link,
      String href) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.link = link;
    this.href = href;
  }

  @Override public int hashCode() {
    return Objects.hash(id);
  }

  @Override public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (!(obj instanceof TreasureResource)) {
      return false;
    }

    TreasureResource other = (TreasureResource) obj;
    return Objects.equals(this.id, other.id);
  }

  @Override public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("id", id)
        .add("name", name)
        .toString();
  }
}
