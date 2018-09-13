package controllers;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import core.model.Token;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.*;
import service.treasure.TreasureResource;
import service.treasure.TreasureService;

/**
 * 主页控制器。
 *
 * @author playframework
 * @author mrzhqiang
 */
@Singleton
public final class HomeController extends Controller {
  private final TreasureService treasureService;
  private final TokenService tokenService;
  private final HttpExecutionContext ec;

  @Inject
  public HomeController(TreasureService treasureService, HttpExecutionContext ec) {
    this.treasureService = treasureService;
    this.ec = ec;
  }

  /**
   * 首页，展示宝藏列表。
   */
  public CompletionStage<Result> index() {
    String value = request().cookie(Token.COL_ACCESS_TOKEN).value();

    return treasureService.list().thenApplyAsync(
        stream -> {
          List<TreasureResource> resourceList = stream.collect(Collectors.toList());
          return ok(views.html.index.render(resourceList));
        },
        ec.current());
  }
}
