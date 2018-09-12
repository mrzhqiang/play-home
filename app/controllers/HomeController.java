package controllers;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.concurrent.CompletionStage;
import play.libs.ws.WSClient;
import play.mvc.*;
import rest.RestParser;
import service.treasure.TreasureResource;

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
    return ws.url(rest.v1.routes.TreasureController.list().absoluteURL(request()))
        .get()
        .thenApply(response -> RestParser.fromListJson(response.asJson(), TreasureResource.class))
        .thenApply(dataList -> ok(views.html.index.render(dataList)));
  }
}
