package core;

import io.ebean.Finder;
import io.ebean.Model;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nonnull;

/**
 * 基于 EBean 的仓库。
 *
 * @author qiang.zhang
 */
public abstract class EBeanRepository<I, E extends EBeanModel> implements Repository<I, E> {
  protected final Finder<I, E> finder;

  protected EBeanRepository(Class<E> entityClass) {
    this.finder = new Finder<>(entityClass);
  }

  @Override public Optional<E> create(E entity) {
    Optional<E> optional = Optional.ofNullable(entity).filter(EBeanModel::checkSelf);
    optional.ifPresent(e -> execute(e, Model::insert));
    return optional;
  }

  @Override public Optional<E> update(I primaryKey, E entity) {
    Optional<E> optional = get(primaryKey).filter(EBeanModel::checkSelf)
        .flatMap(e -> merge(e, entity));
    optional.ifPresent(e -> execute(e, Model::update));
    return optional;
  }

  @Override public Optional<E> get(I primaryKey) {
    return Optional.ofNullable(primaryKey).map(i -> dispose(() -> finder.byId(i)));
  }

  @Override public Optional<E> delete(I primaryKey) {
    Optional<E> optional = get(primaryKey).filter(EBeanModel::checkSelf);
    optional.ifPresent(e -> execute(e, Model::delete));
    return optional;
  }

  @Nonnull @Override public List<E> list() {
    return dispose(finder::all);
  }
}
