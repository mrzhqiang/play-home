package core.dao;

import java.util.Optional;

/**
 * DAO is database basic operation.
 *
 * @param <T> database entity
 * @author mrzhqiang
 */
interface DAO<T> {
  T add(T entity);

  T remove(T entity);

  void remove(Object... objects);

  T modify(T entity);

  Optional<T> find(Object... objects);
}
