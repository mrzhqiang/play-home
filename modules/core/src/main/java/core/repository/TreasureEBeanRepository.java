package core.repository;

import com.google.common.base.Verify;
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
    Verify.verify(Treasures.checkName(name), "invalid name: %s", name);

    int firstRow = computeFirstRow(from, size);
    int maxRows = computeMaxRows(size);
    return provide(() -> {
      PagedList<Treasure> pagedList = finder.query().where()
          .icontains(Treasure.COL_NAME, name)
          .setFirstRow(firstRow)
          .setMaxRows(maxRows)
          .findPagedList();
      return new EBeanPaging<>(pagedList);
    });
  }
}
