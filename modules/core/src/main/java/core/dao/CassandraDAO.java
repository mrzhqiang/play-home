package core.dao;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.mapping.Mapper;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import core.BasicDAO;
import core.Cassandra;
import core.entity.Treasure;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.UrlHelper;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
import static core.common.CassandraConstant.COMMON_COLUMN_CREATED;
import static core.common.CassandraConstant.COMMON_COLUMN_DESCRIPTION;
import static core.common.CassandraConstant.COMMON_COLUMN_LINK;
import static core.common.CassandraConstant.COMMON_COLUMN_NAME;
import static core.common.CassandraConstant.COMMON_COLUMN_UPDATED;
import static core.common.CassandraConstant.TREASURE_ID;

/**
 * 基于 Cassandra 的实体对象映射器。
 *
 * @author mrzhqiang
 */
abstract class CassandraDAO<T> implements BasicDAO<T> {
  private static final Logger logger = LoggerFactory.getLogger("core");

  static BasicDAO<Treasure> treasure(Cassandra cassandra) {
    return new CassandraTreasureDAO(cassandra);
  }

  /**
   * 获得 Cassandra 实体映射器。
   */
  @Nullable
  @CheckReturnValue
  abstract Mapper<T> getMapper();

  @Override public boolean add(T entity) {
    try {
      Preconditions.checkNotNull(getMapper()).save(check(entity), Mapper.Option.ifNotExists(true));
      return true;
    } catch (Exception e) {
      logger.error("Insert entity failed: " + entity, e);
    }
    return false;
  }

  @CheckReturnValue
  @Override
  public Optional<T> find(Object... objects) {
    try {
      Preconditions.checkNotNull(objects, "objects");
      return Optional.ofNullable(Preconditions.checkNotNull(getMapper()).get(objects));
    } catch (Exception e) {
      logger.error("Select entity failed by primary key: " + Arrays.toString(objects), e);
    }
    return Optional.empty();
  }

  @Override public boolean update(T entity, Object... objects) {
    try {
      Optional<T> optional = find(objects);
      if (optional.isPresent()) {
        T oldEntity = optional.get();
        Preconditions.checkNotNull(getMapper()).save(merge(oldEntity, entity));
        return true;
      }
    } catch (Exception e) {
      logger.error("Update entity failed: " + entity, e);
    }
    return false;
  }

  @Override public boolean remove(T entity) {
    try {
      Preconditions.checkNotNull(getMapper()).delete(check(entity));
      return true;
    } catch (Exception e) {
      logger.error("Delete entity failed: " + entity, e);
    }
    return false;
  }

  @Override public boolean remove(Object... objects) {
    try {
      Preconditions.checkNotNull(getMapper())
          .delete(Preconditions.checkNotNull(objects, "objects"));
      return true;
    } catch (Exception e) {
      logger.error("Delete entity failed by primary key: " + Arrays.toString(objects), e);
    }
    return false;
  }

  @Nonnull
  @CheckReturnValue
  @Override
  public List<T> execute(String query) {
    try {
      Preconditions.checkNotNull(query, "query");
      Preconditions.checkArgument(!query.isEmpty(), "query must not empty.");
      ResultSet resultSet =
          Preconditions.checkNotNull(getMapper()).getManager().getSession().execute(query);
      return getMapper().map(resultSet).all();
    } catch (Exception e) {
      logger.error("Statement execute failed: " + query, e);
    }
    return Collections.emptyList();
  }

  private static final class CassandraTreasureDAO extends CassandraDAO<Treasure> {
    private final Cassandra cassandra;

    private CassandraTreasureDAO(Cassandra cassandra) {
      this.cassandra = cassandra;
    }

    @Nullable @Override public Mapper<Treasure> getMapper() {
      return cassandra.mapper(Treasure.class);
    }

    @Nonnull @Override public Treasure check(Treasure entity) {
      checkNotNull(entity, "entity");
      checkState(entity.id != null, TREASURE_ID);
      checkState(!Strings.isNullOrEmpty(entity.name), COMMON_COLUMN_NAME);
      checkState(!Strings.isNullOrEmpty(entity.description), COMMON_COLUMN_DESCRIPTION);
      checkState(UrlHelper.simpleCheck(entity.link), COMMON_COLUMN_LINK);
      checkState(entity.created != null, COMMON_COLUMN_CREATED);
      checkState(entity.updated != null, COMMON_COLUMN_UPDATED);
      return entity;
    }

    @Nonnull @Override public Treasure merge(Treasure oldEntity, Treasure newEntity) {
      checkNotNull(oldEntity, "oldEntity");
      checkNotNull(newEntity, "newEntity");
      Treasure entity = new Treasure();
      entity.id = oldEntity.id;
      entity.name = Strings.isNullOrEmpty(newEntity.name) ? oldEntity.name : newEntity.name;
      entity.description = Strings.isNullOrEmpty(newEntity.description) ? oldEntity.description
          : newEntity.description;
      entity.link = UrlHelper.simpleCheck(newEntity.link) ? newEntity.link : oldEntity.link;
      entity.created = oldEntity.created;
      entity.updated = new Date();
      return entity;
    }
  }
}
