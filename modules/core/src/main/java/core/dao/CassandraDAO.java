package core.dao;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Statement;
import com.datastax.driver.mapping.Mapper;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.*;
import static com.google.common.base.Preconditions.*;

/**
 * A cassandra based DAO.
 *
 * @param <T> database entity
 * @author mrzhqiang
 */
interface CassandraDAO<T> extends DAO<T> {
  default T add(T entity) {
    getMapper().save(entity, Mapper.Option.ifNotExists(true));
    return entity;
  }

  default T remove(T entity) {
    getMapper().delete(entity);
    return entity;
  }

  default void remove(Object... objects) {
    requireNonNull(objects, "objects");
    checkArgument(objects.length > 0, "objects length is 0");
    getMapper().delete(objects);
  }

  default T modify(T entity) {
    getMapper().save(entity);
    return entity;
  }

  default Optional<T> find(Object... objects) {
    requireNonNull(objects, "objects");
    checkArgument(objects.length > 0, "objects length is 0");
    return Optional.ofNullable(getMapper().get(objects));
  }

  default List<T> find(Statement queryStatement) {
    requireNonNull(queryStatement, "queryStatement");
    ResultSet resultSet = getMapper().getManager().getSession().execute(queryStatement);
    return getMapper().map(resultSet).all();
  }

  Mapper<T> getMapper();
}
