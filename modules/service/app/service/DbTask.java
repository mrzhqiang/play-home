package service;

import akka.actor.ActorSystem;
import core.Cassandra;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import scala.concurrent.duration.Duration;

/**
 * 数据库相关任务，实际上是为了初始化数据库结构。
 *
 * @author mrzhqiang
 */
public final class DbTask {
  private final Cassandra cassandra;
  private final ActorSystem actorSystem;
  private final DatabaseExecutionContext executionContext;

  @Inject
  public DbTask(Cassandra cassandra, ActorSystem actorSystem,
      DatabaseExecutionContext executionContext) {
    this.cassandra = cassandra;
    this.actorSystem = actorSystem;
    this.executionContext = executionContext;
    this.initialize();
  }

  private void initialize() {
    actorSystem.scheduler().scheduleOnce(
        Duration.create(1, TimeUnit.SECONDS),
        cassandra::init,
        executionContext
    );
  }
}
