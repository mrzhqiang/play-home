package core.repository;

import core.entity.EBeanModel;
import core.entity.Entity;
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

  @Nonnull @Override public Optional<E> create(E entity) {
    return Optional.ofNullable(entity)
        .filter(Entity::checkSelf)
        .map(e -> execute(e, Model::insert));
  }

  @Nonnull @Override public Optional<E> update(E entity) {
    return Optional.ofNullable(entity)
        .filter(Entity::checkSelf)
        .map(e -> execute(e, Model::update));
  }

  @Nonnull @Override public Optional<E> get(I primaryKey) {
    return Optional.ofNullable(primaryKey).map(i -> dispose(() -> finder.byId(i)));
  }

  @Nonnull @Override public Optional<E> deleteBy(I primaryKey) {
    return Optional.ofNullable(primaryKey)
        .flatMap(this::get)
        .filter(e -> dispose(e::delete));
  }

  @Nonnull @Override public Optional<E> delete(E entity) {
    return Optional.ofNullable(entity)
        .filter(Entity::checkSelf)
        .filter(e -> dispose(e::delete));
  }

  @Nonnull @Override public Paging<E> list(int from, int size) {
    PagedList<E> pagedList =
        dispose(() -> finder.query().setFirstRow(from).setMaxRows(size).findPagedList());
    return new EBeanPaging<>(pagedList);
  }

  @Nonnull @Override public List<E> list() {
    return dispose(finder::all);
  }
}
