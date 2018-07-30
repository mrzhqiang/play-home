package service;

import akka.actor.ActorSystem;
import javax.inject.Inject;
import javax.inject.Singleton;
import play.libs.concurrent.CustomExecutionContext;

/**
 * 数据库执行上下文。
 *
 * @author qiang.zhang
 */
@Singleton
public final class DatabaseExecutionContext extends CustomExecutionContext {
  @Inject
  public DatabaseExecutionContext(ActorSystem actorSystem) {
    super(actorSystem, "database.dispatcher");
  }
}
