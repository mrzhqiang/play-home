package controllers;

import java.util.concurrent.CompletionStage;
import javax.inject.Inject;
import javax.inject.Singleton;
import play.libs.ws.WSClient;
import play.mvc.*;
import rest.RestParser;
import rest.v1.treasure.TreasureResource;

/**
 * 主页控制器。
 *
 * @author playframework
 * @author mrzhqiang
 */
@Singleton
public final class HomeController extends Controller {

  private final WSClient ws;

  @Inject
  public HomeController(WSClient ws) {
    this.ws = ws;
  }

  /** 首页，展示宝藏列表。 */
  public CompletionStage<Result> index() {
    return ws.url(rest.v1.treasure.routes.TreasureController.list().absoluteURL(request()))
        .get()
        .thenApply(wsResponse -> ok(views.html.index.render(
            RestParser.fromListJson(wsResponse.asJson(), TreasureResource.class))));
  }
}
