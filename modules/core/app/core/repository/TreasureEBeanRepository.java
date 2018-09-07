package core.repository;

import com.google.common.base.Preconditions;
import com.google.inject.Singleton;
import core.entity.Treasure;
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
    Preconditions.checkNotNull(name);
    Preconditions.checkArgument(!name.isEmpty() && name.length() <= 12);

    int firstRow = from < 0 ? 0 : from;
    int maxRows = size < 10 ? 10 : size;
    PagedList<Treasure> pagedList = dispose(() ->
        finder.query().where()
            .icontains(Treasure.COL_NAME, name)
            .setFirstRow(firstRow)
            .setMaxRows(maxRows)
            .findPagedList());
    return new EBeanPaging<>(pagedList);
  }
}
