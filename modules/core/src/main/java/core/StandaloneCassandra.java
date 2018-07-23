package core;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ProtocolOptions;
import com.datastax.driver.core.QueryLogger;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import javax.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static core.common.CassandraConstant.*;
import static com.datastax.driver.core.querybuilder.QueryBuilder.*;

/**
 * @author mrzhqiang
 */
@Singleton final class StandaloneCassandra implements Cassandra {
  private static final Logger logger = LoggerFactory.getLogger("core");

  private static final String ROOT_PATH = "core.cassandra";
  private static final String HOST = ROOT_PATH + ".host";
  private static final String PORT = ROOT_PATH + ".port";
  private static final String MAX_SECONDS = ROOT_PATH + ".maxSeconds";
  private static final String DEFAULT_HOST = "localhost";
  private static final String CLUSTER_NAME = "cluster_name";
  private static final String RELEASE_VERSION = "release_version";

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
      logger.info("Cassandra cluster and mapping manager create successful.");
    } catch (Exception e) {
      String message = "Cassandra cluster create failed.";
      logger.error(message, e);
      throw new RuntimeException(message, e);
    }
  }

  @Override public void init() {
    try (Session session = cluster.connect()) {
      Row row = session.execute(
          select(CLUSTER_NAME, RELEASE_VERSION)
              .from("system", "local")
      ).one();
      logger.info("Cassandra cluster_name:{} release_version:{}.", row.getString(CLUSTER_NAME),
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

  @Nonnull @CheckReturnValue @Override public <T> Mapper<T> mapper(Class<T> clazz) {
    return mappingManager.mapper(clazz);
  }

  @Nonnull @CheckReturnValue @Override public Session getSession() {
    return mappingManager.getSession();
  }
}
