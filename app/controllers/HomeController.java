package controllers;

import core.entity.Treasure;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.inject.Singleton;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.*;
import rest.v1.treasure.TreasureResource;
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
        .thenApplyAsync(treasureStream -> ok(views.html.index.render(treasureStream.map(
            treasure -> {
              TreasureResource treasureResource = new TreasureResource();
              treasureResource.setName(treasure.name);
              treasureResource.setLink(treasure.link);
              treasureResource.setDescription(treasure.description);
              return treasureResource;
            }).collect(Collectors.toList()))),
            httpExecution.current());
  }
}
