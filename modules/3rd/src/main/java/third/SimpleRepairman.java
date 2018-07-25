package third;

import core.Cassandra;
import core.Redis;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A simplest repairman.
 *
 * @author mrzhqiang
 */
@Singleton final class SimpleRepairman implements Repairman {
  private static final Logger logger = LoggerFactory.getLogger(SimpleRepairman.class);

  private final Redis redis;
  private final Cassandra cassandra;

  @Inject SimpleRepairman(Redis redis, Cassandra cassandra) {
    this.redis = redis;
    this.cassandra = cassandra;
  }

  @Override public void repair() {
    logger.info("repair start...");
    redis.init();
    cassandra.init();
    logger.info("repair end.");
  }
}
