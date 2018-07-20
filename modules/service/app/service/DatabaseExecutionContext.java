package service;

import akka.actor.ActorSystem;
import javax.inject.Inject;
import play.libs.concurrent.CustomExecutionContext;

/**
 * @author qiang.zhang
 */
public final class DatabaseExecutionContext extends CustomExecutionContext {
  @Inject
  public DatabaseExecutionContext(ActorSystem actorSystem) {
    super(actorSystem, "contexts.database.dispatcher");
  }
}
