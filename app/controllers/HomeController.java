package controllers;

import core.entity.Treasure;
import framework.JsonList;
import java.util.List;
import java.util.concurrent.CompletionStage;
import javax.inject.Inject;
import javax.inject.Singleton;
import play.libs.ws.WSClient;
import play.mvc.*;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
@Singleton
public final class HomeController extends Controller {
  private final WSClient ws;

  @Inject
  public HomeController(WSClient ws) {
    this.ws = ws;
  }

  /**
   * An action that renders an HTML page with a welcome message.
   * The configuration in the <code>routes</code> file means that
   * this method will be called when the application receives a
   * <code>GET</code> request with a path of <code>/</code>.
   */
  public CompletionStage<Result> index() {
    return ws.url("http://localhost:9000/v1/treasures").get().thenApply(wsResponse -> {
      List<Treasure> treasureList = JsonList.from(wsResponse.asJson(), Treasure.class);
      return ok(views.html.index.render(treasureList));
    });
  }
}
