package rest.v1.treasure;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import core.entity.Treasure;
import framework.ApplicationException;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import rest.RestHelper;
import service.treasure.TreasureService;

import static framework.RequestParser.*;
import static play.libs.Json.toJson;

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
    ApplicationException.badRequest(jsonNode != null && jsonNode.isObject(),
        "Json null or not object.");
    TreasureResource resource = fromJson(jsonNode, TreasureResource.class);
    return treasureService.create(toData(resource))
        .thenApplyAsync(data -> created(toJson(fromData(data))),
            httpExecution.current());
  }

  // TODO: 2018/7/24 需要权限操作
  public CompletionStage<Result> show(String id) {
    UUID treasureId = asUUID(id);
    return treasureService.get(treasureId)
        .thenApplyAsync(data -> ok(toJson(fromData(data))),
            httpExecution.current());
  }

  public CompletionStage<Result> list() {
    return treasureService.list()
        .thenApplyAsync(
            dataStream -> ok(toJson(dataStream.map(this::fromData).collect(Collectors.toList()))),
            httpExecution.current());
  }

  public CompletionStage<Result> search(String name) {
    return treasureService.get(name)
        .thenApplyAsync(
            dataStream -> ok(toJson(dataStream.map(this::fromData).collect(Collectors.toList()))),
            httpExecution.current());
  }

  @BodyParser.Of(BodyParser.Json.class)
  public CompletionStage<Result> update(String id) {
    UUID treasureId = asUUID(id);
    JsonNode jsonNode = request().body().asJson();
    TreasureResource resource = fromJson(jsonNode, TreasureResource.class);
    return treasureService.update(treasureId, toData(resource))
        .thenApplyAsync(data -> ok(toJson(fromData(data))),
            httpExecution.current());
  }

  public CompletionStage<Result> delete(String id) {
    UUID treasureId = asUUID(id);
    return treasureService.delete(treasureId)
        .thenApplyAsync(data -> ok(toJson(fromData(data))),
            httpExecution.current());
  }

  private Treasure toData(TreasureResource resource) {
    Treasure treasure = new Treasure();
    treasure.id = UUID.randomUUID();
    treasure.name = resource.getName();
    treasure.description = resource.getDescription();
    treasure.link = resource.getLink();
    treasure.created = new Date();
    treasure.updated = new Date();
    return treasure;
  }

  private TreasureResource fromData(Treasure data) {
    TreasureResource resource = new TreasureResource();
    resource.setName(data.name);
    resource.setDescription(data.description);
    resource.setLink(data.link);
    resource.setHref(RestHelper.href("v1", "treasures", data.id.toString()));
    return resource;
  }
}
