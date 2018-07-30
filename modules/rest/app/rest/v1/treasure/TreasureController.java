package rest.v1.treasure;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import core.entity.Treasure;
import java.util.UUID;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import service.treasure.TreasureService;

import static play.libs.Json.toJson;
import static rest.BodyParser.asLong;
import static rest.BodyParser.fromJson;

/**
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
    TreasureResource resource = fromJson(jsonNode, TreasureResource.class);
    return treasureService.create(resource.getName(), resource.getLink(), resource.getDescription())
        .thenApplyAsync(data -> created(toJson(data)),
            httpExecution.current());
  }

  // TODO: 2018/7/24 需要权限操作
  public CompletionStage<Result> show(String id) {
    long treasureId = asLong(id);
    return treasureService.get(treasureId)
        .thenApplyAsync(data -> ok(toJson(data)),
            httpExecution.current());
  }

  public CompletionStage<Result> list() {
    return treasureService.list()
        .thenApplyAsync(dataStream -> ok(
            toJson(dataStream.collect(Collectors.toList()))),
            httpExecution.current());
  }

  public CompletionStage<Result> search(String name) {
    return treasureService.get(name)
        .thenApplyAsync(dataStream -> ok(toJson(dataStream)),
            httpExecution.current());
  }

  @BodyParser.Of(BodyParser.Json.class)
  public CompletionStage<Result> update(String id) {
    long treasureId = asLong(id);
    JsonNode jsonNode = request().body().asJson();
    TreasureResource resource = fromJson(jsonNode, TreasureResource.class);

    Treasure treasure = new Treasure();
    treasure.name = resource.getName();
    treasure.link = resource.getLink();
    treasure.description = resource.getDescription();
    return treasureService.update(treasureId, treasure)
        .thenApplyAsync(data -> ok(toJson(data)),
            httpExecution.current());
  }

  public CompletionStage<Result> delete(String id) {
    long treasureId = asLong(id);
    return treasureService.delete(treasureId)
        .thenApplyAsync(data -> ok(toJson(data)),
            httpExecution.current());
  }
}
