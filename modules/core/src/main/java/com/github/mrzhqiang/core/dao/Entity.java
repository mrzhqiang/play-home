package com.github.mrzhqiang.core.dao;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Statement;
import com.datastax.driver.mapping.Mapper;
import java.util.List;
import java.util.Optional;

interface Entity<T> {
  default void add(T entity) {
    getMapper().save(entity, Mapper.Option.ifNotExists(true));
  }

  default void delete(T entity) {
    getMapper().delete(entity);
  }

  default void delete(Object... objects) {
    getMapper().delete(objects);
  }

  default void update(T entity) {
    getMapper().save(entity);
  }

  default Optional<T> find(Object... objects) {
    return Optional.ofNullable(getMapper().get(objects));
  }

  default Optional<T> find(Statement queryStatement) {
    ResultSet resultSet = getMapper().getManager().getSession().execute(queryStatement);
    return Optional.ofNullable(getMapper().map(resultSet).one());
  }

  default List<T> findAll(Statement queryStatement) {
    ResultSet resultSet = getMapper().getManager().getSession().execute(queryStatement);
    return getMapper().map(resultSet).all();
  }

  Mapper<T> getMapper();
}
