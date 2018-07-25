package rest;

import core.entity.Treasure;
import java.util.Date;
import java.util.UUID;
import rest.v1.treasure.TreasureResource;

/**
 * @author mrzhqiang
 */
public final class Resources {
  private Resources() {
  }

  public static Treasure toData(TreasureResource resource) {
    Treasure treasure = new Treasure();
    treasure.id = UUID.randomUUID();
    treasure.name = resource.getName();
    treasure.description = resource.getDescription();
    treasure.link = resource.getLink();
    treasure.created = new Date();
    treasure.updated = new Date();
    return treasure;
  }

  public static TreasureResource fromData(Treasure data) {
    TreasureResource resource = new TreasureResource();
    resource.setName(data.name);
    resource.setDescription(data.description);
    resource.setLink(data.link);
    resource.setHref(RestHelper.href("v1", "treasures", data.id.toString()));
    return resource;
  }
}
