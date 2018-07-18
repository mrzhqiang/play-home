package com.github.mrzhqiang.core;

import com.datastax.driver.mapping.MappingManager;
import com.google.inject.ImplementedBy;

/**
 * Cassandra driver interface.
 *
 * @author mrzhqiang
 */
@ImplementedBy(SingleCassandra.class)
public interface Cassandra {
  /**
   * You should call this method if cassandra for the first time to run.
   * It is will create all schema, include KEYSPACE, TABLE, INDEX and UTD(User-Defined Types), etc.
   */
  void check();

  /**
   * Examples:
   * <pre>
   *   MappingManager manager = cassandra.getMappingManager();
   *   Mapper<User> userMapper = manager.mapper(User.class);
   *   User user = new User();
   *   // user set something...
   *   userMapper.saveQuery(user);
   * </pre>
   */
  MappingManager getMappingManager();
}
