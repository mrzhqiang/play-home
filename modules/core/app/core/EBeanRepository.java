package core;

import core.exception.DatabaseException;
import io.ebean.Finder;
import io.ebean.Model;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import javax.annotation.Nonnull;
import play.Logger;

/**
 * 基于 EBean 的仓库。
 *
 * @author qiang.zhang
 */
public abstract class EBeanRepository<I, T extends BaseModel>
    implements Repository<I, T>, Predicate<T> {
  private static final Logger.ALogger logger = Logger.of("core");

  @Nonnull abstract protected Finder<I, T> finder();

  @Nonnull abstract protected T merge(T oldEntity, T newEntity);

  protected T safeAccept(T entity, Consumer<T> consumer) {
    Objects.requireNonNull(entity, "entity");
    Objects.requireNonNull(consumer, "consumer");
    test(entity);
    try {
      consumer.accept(entity);
      return entity;
    } catch (Exception e) {
      String message = "EBean Model execution failed: " + e.getMessage();
      logger.error(message);
      throw new DatabaseException(message);
    }
  }

  @Override public Optional<T> create(T entity) {
    return Optional.ofNullable(entity).map(newEntity -> safeAccept(newEntity, Model::insert));
  }

  @Override public Optional<T> update(I primaryKey, T entity) {
    return get(primaryKey).map(t -> merge(t, entity)).map(t -> safeAccept(t, Model::update));
  }

  @Override default Optional<T> get(I primaryKey) {
    return Optional.ofNullable().map(i -> safeAccept(, ));
  }

  @Override default Optional<T> delete(I primaryKey) {
    return get(primaryKey).filter(oldEntity -> {
      try {
        return oldEntity.delete();
      } catch (Exception e) {
        String message = "delete error: " + e.getMessage();
        logger.error(message);
        throw new DatabaseException(message);
      }
    });
  }

  @Nonnull @Override default List<T> list() {
    try {
      return finder().all();
    } catch (Exception e) {
      String message = "list error: " + e.getMessage();
      logger.error(message);
      throw new DatabaseException(message);
    }
  }
}
