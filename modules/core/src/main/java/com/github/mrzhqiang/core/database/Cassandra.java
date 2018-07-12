package com.github.mrzhqiang.core.database;

import com.datastax.driver.core.ProtocolOptions;
import com.datastax.driver.mapping.MappingManager;
import com.google.inject.ImplementedBy;

/**
 * Cassandra driver interface.
 *
 * @author mrzhqiang
 */
@ImplementedBy(SingleCassandra.class)
public interface Cassandra {
  String ROOT_PATH = "core.cassandra";
  String HOST = ROOT_PATH + ".host";
  String PORT = ROOT_PATH + ".port";
  String MAX_SECONDS = ROOT_PATH + ".maxSeconds";

  String DEFAULT_HOST = "localhost";
  int DEFAULT_PORT = ProtocolOptions.DEFAULT_PORT;
  int DEFAULT_MAX_SECONDS = ProtocolOptions.DEFAULT_MAX_SCHEMA_AGREEMENT_WAIT_SECONDS;

  String RELEASE_VERSION = "release_version";
  String QUERY_RELEASE_VERSION = "select " + RELEASE_VERSION + " from system.local";

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
