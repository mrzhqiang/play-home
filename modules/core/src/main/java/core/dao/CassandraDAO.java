package core.dao;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Statement;
import com.datastax.driver.mapping.Mapper;
import com.google.common.base.Preconditions;
import core.Entity;
import java.util.List;
import java.util.Optional;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;

/**
 * Cassandra DAO 接口。
 * <p>
 * 实现基本的数据库 CRUD 操作，以及执行一些实体相关的查询语句。
 *
 * @param <T> 数据实体类。
 * @author mrzhqiang
 */
interface CassandraDAO<T extends Entity<T>> extends BasicDAO<T> {
  /**
   * 获得实体映射器。
   * <p>
   * 一般来说，不要在 core 之外调用这个方法，除非迫不得已。
   *
   * @return 实体映射器。
   */
  @Nonnull
  @CheckReturnValue
  Mapper<T> getMapper();

  @Nonnull default T add(T entity) {
    Preconditions.checkNotNull(entity, "entity").check();
    getMapper().save(entity, Mapper.Option.ifNotExists(true));
    return entity;
  }

  @Nonnull default T remove(T entity) {
    Preconditions.checkNotNull(entity, "entity").check();
    getMapper().delete(entity);
    return entity;
  }

  default void remove(Object... objects) {
    Preconditions.checkNotNull(objects, "objects");
    Preconditions.checkArgument(objects.length > 0, "objects length is 0");
    getMapper().delete(objects);
  }

  @Nonnull default T modify(T oldEntity, T newEntity) {
    Preconditions.checkNotNull(oldEntity, "entity").check();
    T entity = oldEntity.merge(newEntity);
    getMapper().save(entity);
    return entity;
  }

  @CheckReturnValue
  default Optional<T> find(Object... objects) {
    Preconditions.checkNotNull(objects, "objects");
    Preconditions.checkArgument(objects.length > 0, "objects length is 0");
    return Optional.ofNullable(getMapper().get(objects));
  }

  /**
   * 通过语句查询实体列表。
   *
   * @param queryStatement 查询语句对象。
   * @return 实体列表。
   */
  @Nonnull
  @CheckReturnValue
  default List<T> find(Statement queryStatement) {
    Preconditions.checkNotNull(queryStatement, "queryStatement");
    ResultSet resultSet = getMapper().getManager().getSession().execute(queryStatement);
    return getMapper().map(resultSet).all();
  }
}
