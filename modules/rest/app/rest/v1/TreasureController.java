package rest.v1;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import service.treasure.TreasureResource;
import service.treasure.TreasureService;

import static play.libs.Json.toJson;
import static framework.SimpleParser.asLong;
import static framework.SimpleParser.fromJson;

/**
 * 宝藏控制器。
 *
 * @author mrzhqiang
 */
@Singleton
public final class TreasureController extends Controller {
  private final TreasureService treasureService;
  private final HttpExecutionContext httpExecution;

  @Inject
  public TreasureController(TreasureService treasureService, HttpExecutionContext httpExecution) {
    this.treasureService = treasureService;
    this.httpExecution = httpExecution;
  }

  @BodyParser.Of(BodyParser.Json.class)
  public CompletionStage<Result> create() {
    JsonNode jsonNode = request().body().asJson();
    TreasureResource treasureResource = fromJson(jsonNode, TreasureResource.class);
    return treasureService.create(treasureResource)
        .thenApplyAsync(resource -> created(toJson(resource)),
            httpExecution.current());
  }

  // TODO: 2018/7/24 需要权限操作
  public CompletionStage<Result> show(String id) {
    long treasureId = asLong(id);
    return treasureService.get(treasureId)
        .thenApplyAsync(resource -> ok(toJson(resource)),
            httpExecution.current());
  }

  public CompletionStage<Result> list() {
    return treasureService.list()
        .thenApplyAsync(dataStream -> ok(
            toJson(dataStream.collect(Collectors.toList()))),
            httpExecution.current());
  }

  public CompletionStage<Result> search(String name) {
    return treasureService.search(name)
        .thenApplyAsync(treasurePaging -> ok(toJson(treasurePaging)),
            httpExecution.current());
  }

  @BodyParser.Of(BodyParser.Json.class)
  public CompletionStage<Result> update(String id) {
    long treasureId = asLong(id);
    JsonNode jsonNode = request().body().asJson();
    TreasureResource treasureResource = fromJson(jsonNode, TreasureResource.class);
    return treasureService.update(treasureId, treasureResource)
        .thenApplyAsync(resource -> ok(toJson(resource)),
            httpExecution.current());
  }

  public CompletionStage<Result> delete(String id) {
    long treasureId = asLong(id);
    return treasureService.delete(treasureId)
        .thenApplyAsync(resource -> ok(toJson(resource)),
            httpExecution.current());
  }
}
