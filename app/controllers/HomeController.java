package controllers;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.*;
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
  private final HttpExecutionContext ec;

  @Inject
  public HomeController(TreasureService treasureService,
      HttpExecutionContext ec) {
    this.treasureService = treasureService;
    this.ec = ec;
  }

  /**
   * 首页，展示宝藏列表。
   */
  public CompletionStage<Result> index() {
    return treasureService.list().thenApplyAsync(
        stream -> ok(views.html.index.render(stream.collect(Collectors.toList()))),
        ec.current());
  }
}
