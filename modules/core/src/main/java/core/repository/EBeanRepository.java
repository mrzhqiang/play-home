package core.repository;

import core.Paging;
import core.Repository;
import core.entity.EBeanModel;
import io.ebean.Finder;
import io.ebean.Model;
import io.ebean.PagedList;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nonnull;

/**
 * 基于 EBean 的实体仓库。
 *
 * @author qiang.zhang
 */
abstract class EBeanRepository<I, E extends EBeanModel> extends Finder<I, E>
    implements Repository<I, E> {
  EBeanRepository(Class<E> type) {
    super(type);
  }

  @Nonnull @Override public Optional<E> save(E entity) {
    Optional<E> optional = Optional.ofNullable(entity);
    optional.ifPresent(e -> execute(e, Model::save));
    return optional;
  }

  @Nonnull @Override public Optional<E> delete(I primaryKey) {
    Optional<E> optional = Optional.ofNullable(primaryKey).flatMap(this::get);
    optional.ifPresent(e -> execute(e, Model::delete));
    return optional;
  }

  @Nonnull @Override public Optional<E> get(I primaryKey) {
    return provide(() -> Optional.ofNullable(primaryKey).map(this::byId));
  }

  @Nonnull @Override public Paging<E> list(int from, int size) {
    return provide(() -> {
      PagedList<E> pagedList = query()
          .setFirstRow(from)
          .setMaxRows(size)
          .findPagedList();
      return new EBeanPaging<>(pagedList);
    });
  }

  @Nonnull @Override public List<E> list() {
    return provide(this::all);
  }

  /**
   * 通过页面索引，计算当前页面第一行的序号。
   */
  int computeFirstRow(int index, int size) {
    if (index < 1) {
      return 1;
    }
    return (index - 1) * computeMaxRows(size) + 1;
  }

  /**
   * 通过页面大小，计算当前页面的最大行数。
   * <p>
   * 注意：最大行数的最小值为 10。
   */
  int computeMaxRows(int size) {
    return size < 10 ? 10 : size;
  }
}
