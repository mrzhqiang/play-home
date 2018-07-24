package rest.v1.treasure;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import framework.ApplicationException;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import service.treasure.TreasureResource;
import service.treasure.TreasureService;

import static framework.RequestParser.*;

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

  // TODO: 2018/7/24 需要权限操作
  public CompletionStage<Result> delete(String id) {
    long treasureId = asLong(id);
    return treasureService.delete(treasureId)
        .thenApplyAsync(resource -> ok(Json.toJson(resource)),
            executionContext.current());
  }

  public CompletionStage<Result> search(String name) {
    return treasureService.get(name)
        .thenApplyAsync(treasureResourceStream ->
                ok(Json.toJson(treasureResourceStream.collect(Collectors.toList()))),
            executionContext.current());
  }

  public CompletionStage<Result> show(String id) {
    long treasureId = asLong(id);
    return treasureService.get(treasureId)
        .thenApplyAsync(resource -> ok(Json.toJson(resource)),
            executionContext.current());
  }

  @BodyParser.Of(BodyParser.Json.class)
  public CompletionStage<Result> update(String id) {
    long treasureId = asLong(id);
    JsonNode jsonNode = request().body().asJson();
    TreasureResource treasureResource = fromJson(jsonNode, TreasureResource.class);
    return treasureService.update(treasureId, treasureResource)
        .thenApplyAsync(resource -> ok(Json.toJson(resource)),
            executionContext.current());
  }

  @BodyParser.Of(BodyParser.Json.class)
  public CompletionStage<Result> create() {
    JsonNode jsonNode = request().body().asJson();
    ApplicationException.badRequest(jsonNode != null && jsonNode.isObject(),
        "Json null or not object.");
    TreasureResource treasureResource = fromJson(jsonNode, TreasureResource.class);
    return treasureService.create(treasureResource)
        .thenApplyAsync(resource -> ok(Json.toJson(resource)),
            executionContext.current());
  }

  public CompletionStage<Result> list() {
    return treasureService.list()
        .thenApplyAsync(
            treasureStream -> ok(Json.toJson(treasureStream.collect(Collectors.toList()))),
            executionContext.current());
  }
}
