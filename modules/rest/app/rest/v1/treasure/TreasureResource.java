package rest.v1.treasure;

import com.google.common.base.MoreObjects;
import core.entity.Treasure;
import rest.RestHelper;

/**
 * 宝藏资源。
 *
 * @author mrzhqiang
 */
public final class TreasureResource {
  public String name;
  public String link;
  public String description;
  public String href;

  static TreasureResource of(Treasure treasure) {
    TreasureResource resource = new TreasureResource();
    resource.name = treasure.name;
    resource.link = treasure.link;
    resource.description = treasure.description;
    resource.href = RestHelper.href("v1", "treasures", String.valueOf(treasure.id));
    return resource;
  }

  Treasure toTreasure() {
    Treasure treasure = new Treasure();
    treasure.name = name;
    treasure.link = link;
    treasure.description = description;
    return treasure;
  }

  @Override public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("name", name)
        .add("description", description)
        .add("link", link)
        .add("href", href)
        .toString();
  }
}
