package controllers;

import core.entity.Treasure;
import framework.JsonUtil;
import java.util.List;
import java.util.concurrent.CompletionStage;
import javax.inject.Inject;
import javax.inject.Singleton;
import play.libs.ws.WSClient;
import play.mvc.*;

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
        .thenApply(wsResponse -> {
          List<Treasure> treasureList = JsonUtil.fromList(wsResponse.asJson(), Treasure.class);
          return ok(views.html.index.render(treasureList));
        });
  }
}
