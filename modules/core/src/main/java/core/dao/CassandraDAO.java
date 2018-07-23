package core.dao;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Statement;
import com.datastax.driver.mapping.Mapper;
import core.common.Entity;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;

import static com.google.common.base.Preconditions.*;

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
    getMapper().save(check(entity), Mapper.Option.ifNotExists(true));
    return entity;
  }

  @Nonnull default T remove(T entity) {
    getMapper().delete(check(entity));
    return entity;
  }

  default void remove(Object... objects) {
    Objects.requireNonNull(objects, "objects");
    checkArgument(objects.length > 0, "objects length is 0");
    getMapper().delete(objects);
  }

  @Nonnull default T modify(T oldEntity, T newEntity) {
    Objects.requireNonNull(newEntity, "newEntity");
    T merge = check(oldEntity).merge(newEntity);
    getMapper().save(merge);
    return merge;
  }

  @CheckReturnValue
  default Optional<T> find(Object... objects) {
    Objects.requireNonNull(objects, "objects");
    checkArgument(objects.length > 0, "objects length is 0");
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
    Objects.requireNonNull(queryStatement, "queryStatement");
    ResultSet resultSet = getMapper().getManager().getSession().execute(queryStatement);
    return getMapper().map(resultSet).all();
  }
}
