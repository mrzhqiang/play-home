package core.repository;

import com.google.common.base.Preconditions;
import core.entity.BasicModel;
import io.ebean.Finder;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nonnull;
import play.Logger;

/**
 * 基于 EBean 的仓库。
 *
 * @author qiang.zhang
 */
abstract class EBeanRepository<I, T extends BasicModel> implements Repository<I, T> {
  private static final Logger.ALogger logger = Logger.of("core");
  private static final String MESSAGE = "{} execution {} with {} failed, be cause: {}.";

  final Finder<I, T> find;
  private final String entityName;

  EBeanRepository(Class<T> entityClass) {
    this.find = new Finder<>(entityClass);
    this.entityName = entityClass.getName();
  }

  abstract T merge(T oldEntity, T newEntity);

  void logger(String operation, Object with, Throwable cause) {
    logger.error(MESSAGE, entityName, operation, String.valueOf(with), cause.getMessage());
  }

  @Nonnull @Override public List<T> list() {
    try {
      return find.all();
    } catch (Exception e) {
      logger("list", "nothing", e);
      throw new RuntimeException("list error: " + e.getMessage());
    }
  }

  @Nonnull @Override public T create(T entity) {
    Preconditions.checkNotNull(entity, "entity").check();
    try {
      entity.insert();
      return entity;
    } catch (Exception e) {
      logger("create", entity, e);
      throw new RuntimeException("create error: " + e.getMessage());
    }
  }

  @Override public Optional<T> delete(I primaryKey) {
    return get(primaryKey).filter(t -> {
      try {
        return t.delete();
      } catch (Exception e) {
        logger("delete", t, e);
        throw new RuntimeException("delete error: " + e.getMessage());
      }
    });
  }

  @Override public Optional<T> update(I primaryKey, T entity) {
    return get(primaryKey).filter(t -> {
      merge(t, entity).check();
      try {
        t.update();
        return true;
      } catch (Exception e) {
        logger("update", entity, e);
        throw new RuntimeException("update error: " + e.getMessage());
      }
    });
  }

  @Override public Optional<T> get(I primaryKey) {
    return Optional.ofNullable(primaryKey).map(i -> {
      try {
        return find.byId(primaryKey);
      } catch (Exception e) {
        logger("get", primaryKey, e);
        throw new RuntimeException("get error: " + e.getMessage());
      }
    });
  }
}
