package service;

import akka.actor.ActorSystem;
import core.Redis;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import play.Logger;
import scala.concurrent.duration.Duration;

/**
 * 数据库任务。
 * <p>
 * 通常来说，是给 Redis 执行数据初始化工作，目的是校验数据一致性。
 *
 * @author mrzhqiang
 */
public final class DbTask {
  private static final Logger.ALogger logger = Logger.of("service");

  private final Redis redis;
  private final ActorSystem actorSystem;
  private final DatabaseExecutionContext executionContext;

  @Inject
  public DbTask(Redis redis, ActorSystem actorSystem,
      DatabaseExecutionContext executionContext) {
    this.redis = redis;
    this.actorSystem = actorSystem;
    this.executionContext = executionContext;
    this.initialize();
  }

  private void initialize() {
    logger.info("Database Task initialize begin.");
    actorSystem.scheduler().scheduleOnce(
        Duration.create(1, TimeUnit.SECONDS),
        () -> {
          redis.init();
          logger.info("Database Task initialize end.");
        },
        executionContext
    );
  }
}
