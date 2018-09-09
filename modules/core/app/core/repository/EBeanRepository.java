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
abstract class EBeanRepository<I, E extends EBeanModel> implements Repository<I, E> {
  final Finder<I, E> finder;

  EBeanRepository(Class<E> entityClass) {
    this.finder = new Finder<>(entityClass);
  }

  @Override public void save(E entity) {
    execute(entity, Model::save);
  }

  @Override public void delete(E entity) {
    execute(entity, Model::delete);
  }

  @Override public void delete(I primaryKey) {
    execute(primaryKey, finder::deleteById);
  }

  @Nonnull @Override public Optional<E> get(I primaryKey) {
    return provide(() -> Optional.ofNullable(primaryKey).map(finder::byId));
  }

  @Nonnull @Override public Paging<E> list(int from, int size) {
    return provide(() -> {
      PagedList<E> pagedList = finder.query()
          .setFirstRow(from)
          .setMaxRows(size)
          .findPagedList();
      return new EBeanPaging<>(pagedList);
    });
  }

  @Nonnull @Override public List<E> list() {
    return provide(finder::all);
  }
}
