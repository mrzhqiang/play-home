package rest.v1.treasure;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
import service.treasure.TreasureService;

/**
 * @author mrzhqiang
 */
@Singleton
public final class TreasureController extends Controller {
  private final TreasureService treasureService;
  private final HttpExecutionContext executionContext;

  @Inject
  public TreasureController(TreasureService treasureService,
      HttpExecutionContext executionContext) {
    this.treasureService = treasureService;
    this.executionContext = executionContext;
  }

  public CompletionStage<Result> list() {
    return treasureService.list()
        .thenApplyAsync(
            treasureStream -> ok(Json.toJson(treasureStream.collect(Collectors.toList()))),
            executionContext.current());
  }
}
