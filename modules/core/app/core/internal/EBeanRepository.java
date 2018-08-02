package core.internal;

import core.exception.DatabaseException;
import core.Repository;
import core.BaseModel;
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
interface EBeanRepository<I, T extends BaseModel> extends Repository<I, T> {
  Logger.ALogger logger = Logger.of("core");

  @Nonnull Finder<I, T> finder();

  Optional<T> merge(T oldEntity, T newEntity);

  @Nonnull @Override default List<T> list() {
    try {
      return finder().all();
    } catch (Exception e) {
      String message = "list error: " + e.getMessage();
      logger.error(message);
      throw new DatabaseException(message);
    }
  }

  @Override default Optional<T> create(T entity) {
    return Optional.ofNullable(entity).filter(newEntity -> {
      newEntity.check();
      try {
        newEntity.insert();
        return true;
      } catch (Exception e) {
        String message = "create error: " + e.getMessage();
        logger.error(message);
        throw new DatabaseException(message);
      }
    });
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

  @Override default Optional<T> update(I primaryKey, T entity) {
    return get(primaryKey).filter(oldEntity -> {
      return merge(oldEntity, entity).filter(mergeEntity -> {
        try {
          t.update();
          return true;
        } catch (Exception e) {
          String message = "update error: " + e.getMessage();
          logger.error(message);
          throw new DatabaseException(message);
        }
      }).isPresent();
    });
  }

  @Override default Optional<T> get(I primaryKey) {
    return Optional.ofNullable(primaryKey).map(i -> {
      try {
        return finder().byId(i);
      } catch (Exception e) {
        String message = "get error: " + e.getMessage();
        logger.error(message);
        throw new DatabaseException(message);
      }
    });
  }
}
