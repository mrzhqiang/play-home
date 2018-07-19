package com.github.mrzhqiang.core;

import com.datastax.driver.mapping.MappingManager;
import com.google.inject.ImplementedBy;

/**
 * Cassandra driver interface.
 *
 * @author mrzhqiang
 */
@ImplementedBy(StandaloneCassandra.class)
public interface Cassandra {
  /**
   * You should call this method in [3rd] module if the woof application for the first time to run.
   */
  void init();

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
