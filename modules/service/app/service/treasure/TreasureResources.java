package service.treasure;

import core.entity.Treasure;
import play.mvc.Http;

import static java.util.Objects.*;
import static core.common.CassandraConstant.*;
import static com.google.common.base.Strings.*;
import static com.google.common.base.Preconditions.*;

/**
 * @author mrzhqiang
 */
public final class TreasureResources {
  private TreasureResources() {
  }

  public static TreasureResource from(Treasure data) {
    String id = String.valueOf(data.id);
    String href = Http.Context.current().request().uri() + "/" + id;
    return new TreasureResource(id, data.name, data.description, data.link, href);
  }

  public static Treasure to(TreasureResource resource) {
    check(resource);
    Treasure treasure = new Treasure();
    treasure.name = resource.name;
    treasure.description = resource.description;
    treasure.link = resource.link;
    return treasure;
  }

  public static void check(TreasureResource resource) {
    requireNonNull(resource, "resource");
    checkArgument(!isNullOrEmpty(resource.name), COMMON_COLUMN_NAME);
    checkArgument(!isNullOrEmpty(resource.description), COMMON_COLUMN_DESCRIPTION);
    checkArgument(!isNullOrEmpty(resource.link), COMMON_COLUMN_LINK);
  }
}
