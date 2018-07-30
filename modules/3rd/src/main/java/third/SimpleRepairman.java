package third;

import core.Redis;
import javax.inject.Inject;
import javax.inject.Singleton;
import play.Logger;

/**
 * A simplest repairman.
 *
 * @author mrzhqiang
 */
@Singleton final class SimpleRepairman implements Repairman {
  private static final Logger.ALogger logger = Logger.of("third");

  private final Redis redis;

  @Inject SimpleRepairman(Redis redis) {
    this.redis = redis;
  }

  @Override public void repair() {
    logger.info("repair start...");
    redis.init();
    logger.info("repair end.");
  }
}
