package com.github.mrzhqiang.core.database;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.QueryLogger;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.mapping.MappingManager;
import com.google.inject.Singleton;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import play.Logger;

/**
 * Cassandra of single host.
 *
 * @author mrzhqiang
 */
@Singleton final class SingleCassandra implements Cassandra {
  private static final Logger.ALogger logger = Logger.of("core");
  private static final String CASSANDRA_VERSION = "cassandra version {}";

  private final MappingManager mappingManager;

  public SingleCassandra() {
    try {
      String host = DEFAULT_HOST;
      int port = DEFAULT_PORT;
      int maxSeconds = DEFAULT_MAX_SECONDS;

      Config config = ConfigFactory.load();
      if (config.hasPath(ROOT_PATH)) {
        host = config.getString(HOST);
        port = config.getInt(PORT);
        maxSeconds = config.getInt(MAX_SECONDS);
      }

      Cluster cluster = Cluster.builder()
          .addContactPoint(host)
          .withPort(port)
          .withMaxSchemaAgreementWaitSeconds(maxSeconds)
          .build();
      this.mappingManager = new MappingManager(cluster.connect());
      ResultSet rs = mappingManager.getSession().execute(QUERY_RELEASE_VERSION);
      logger.info(CASSANDRA_VERSION, rs.one().getString(RELEASE_VERSION));

      cluster.register(QueryLogger.builder().build());
    } catch (Exception e) {
      String message = "Create cassandra cluster failed.";
      logger.error(message, e);
      throw new RuntimeException(message, e);
    }
  }

  @Override public MappingManager getMappingManager() {
    return mappingManager;
  }
}
