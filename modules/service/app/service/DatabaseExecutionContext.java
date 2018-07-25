package service;

import akka.actor.ActorSystem;
import javax.inject.Inject;
import javax.inject.Singleton;
import play.libs.concurrent.CustomExecutionContext;

/**
 * @author qiang.zhang
 */
@Singleton
public final class DatabaseExecutionContext extends CustomExecutionContext {
  @Inject
  public DatabaseExecutionContext(ActorSystem actorSystem) {
    super(actorSystem, "service.contexts.database.dispatcher");
  }
}
