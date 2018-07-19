package com.github.mrzhqiang.core;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ProtocolOptions;
import com.datastax.driver.core.QueryLogger;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.MappingManager;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import javax.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.github.mrzhqiang.core.common.CassandraConstant.*;

/**
 * @author mrzhqiang
 */
@Singleton final class StandaloneCassandra implements Cassandra {
  private static final Logger logger = LoggerFactory.getLogger("core");
  private static final String MSG_RELEASE_VERSION =
      "cassandra cluster_name:{} release_version:{}.";

  private final Cluster cluster;
  private final MappingManager mappingManager;

  StandaloneCassandra() {
    try {
      String host = DEFAULT_HOST;
      int port = ProtocolOptions.DEFAULT_PORT;
      int maxSeconds = ProtocolOptions.DEFAULT_MAX_SCHEMA_AGREEMENT_WAIT_SECONDS;

      Config config = ConfigFactory.load();
      if (config.hasPath(ROOT_PATH)) {
        host = config.getString(HOST);
        port = config.getInt(PORT);
        maxSeconds = config.getInt(MAX_SECONDS);
      }

      this.cluster = Cluster.builder()
          .addContactPoint(host)
          .withPort(port)
          .withMaxSchemaAgreementWaitSeconds(maxSeconds)
          .build()
          .register(QueryLogger.builder().build());
      this.mappingManager = new MappingManager(cluster.newSession());
    } catch (Exception e) {
      String message = "Create cassandra cluster failed.";
      logger.error(message, e);
      throw new RuntimeException(message, e);
    }
  }

  @Override public void init() {
    try (Session session = cluster.connect()) {
      Row row = session.execute(QUERY_RELEASE_VERSION).one();
      logger.info(MSG_RELEASE_VERSION, row.getString(CLUSTER_NAME),
          row.getString(RELEASE_VERSION));

      session.execute(CREATE_WOOF);
      session.execute(CREATE_TREASURE);
      session.execute(CREATE_TREASURE_NAME_INDEX);

      logger.info("Cassandra is normal.");
    } catch (Exception e) {
      String message = "Cassandra init failed!";
      logger.error(message, e);
      throw new RuntimeException(message, e);
    }
  }

  @Override public MappingManager getMappingManager() {
    return mappingManager;
  }
}
