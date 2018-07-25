package controllers;

import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.inject.Singleton;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.*;
import rest.Resources;
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
  private final HttpExecutionContext httpExecution;

  @Inject
  public HomeController(TreasureService treasureService,
      HttpExecutionContext httpExecution) {
    this.treasureService = treasureService;
    this.httpExecution = httpExecution;
  }

  /** 首页，展示宝藏列表。 */
  public CompletionStage<Result> index() {
    return treasureService.list()
        .thenApplyAsync(treasureStream -> ok(views.html.index.render(
            treasureStream.map(Resources::fromData).collect(Collectors.toList()))),
            httpExecution.current());
  }
}
