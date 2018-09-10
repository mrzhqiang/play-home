package core.repository;

import com.google.common.base.Preconditions;
import com.google.inject.Singleton;
import core.Paging;
import core.entity.Treasure;
import core.util.Treasures;
import io.ebean.PagedList;
import javax.annotation.Nonnull;

/**
 * 基于 EBean 的宝藏仓库。
 *
 * @author qiang.zhang
 */
@Singleton final class TreasureEBeanRepository extends EBeanRepository<Long, Treasure>
    implements TreasureRepository {
  public TreasureEBeanRepository() {
    super(Treasure.class);
  }

  @Nonnull @Override public Paging<Treasure> search(String name, int from, int size) {
    int firstRow = from < 0 ? 0 : from;
    int maxRows = size < 10 ? 10 : size;
    return provide(() -> {
      Preconditions.checkNotNull(name, "Null name.");
      Preconditions.checkArgument(Treasures.checkName(name), "Invalid name.");
      PagedList<Treasure> pagedList = finder.query().where()
          .icontains(Treasure.COL_NAME, name)
          .setFirstRow(firstRow)
          .setMaxRows(maxRows)
          .findPagedList();
      return new EBeanPaging<>(pagedList);
    });
  }
}
