package service.treasure;

import core.entity.Treasure;
import java.time.Instant;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;
import javax.inject.Inject;
import javax.inject.Singleton;
import service.DatabaseExecutionContext;
import util.NameHelper;
import util.LinkHelper;

import static framework.ApplicationException.*;
import static java.util.concurrent.CompletableFuture.*;

/**
 * @author mrzhqiang
 */
@Singleton final class TreasureServiceImpl implements TreasureService {
  private final DatabaseExecutionContext dbExecution;

  @Inject TreasureServiceImpl(DatabaseExecutionContext dbExecution) {
    this.dbExecution = dbExecution;
  }

  @Override public CompletionStage<Treasure> create(String name, String link, String description) {
    badRequest(NameHelper.notEmpty(name), "Invalid name: " + name);
    badRequest(LinkHelper.simpleCheck(link), "Invalid link: " + link);
    return supplyAsync(() -> {
      Treasure treasure = new Treasure();
      treasure.name = name;
      treasure.link = link;
      treasure.description = description;
      treasure.insert();
      return treasure;
    }, dbExecution);
  }

  @Override public CompletionStage<Treasure> get(Long id) {
    badRequest(id != null && id > 0, "Invalid id: " + id);
    return supplyAsync(() -> {
      Optional<Treasure> optionalTreasure = Optional.ofNullable(Treasure.find.byId(id));
      notFound(optionalTreasure.isPresent(), "Not found id: " + id);
      return optionalTreasure.get();
    }, dbExecution);
  }

  @Override public CompletionStage<Treasure> get(String name) {
    badRequest(NameHelper.notEmpty(name), "Invalid name: " + name);
    return supplyAsync(() -> {
      Optional<Treasure> optionalTreasure =
          Treasure.find.query()
              .where()
              .eq("name", name)
              .findOneOrEmpty();
      notFound(optionalTreasure.isPresent(), "Not found name: " + name);
      return optionalTreasure.get();
    }, dbExecution);
  }

  @Override public CompletionStage<Treasure> update(Long id, Treasure treasure) {
    return get(id).thenApplyAsync(oldTreasure -> {
      oldTreasure.name = NameHelper.notEmpty(treasure.name) ? treasure.name : oldTreasure.name;
      oldTreasure.link = LinkHelper.simpleCheck(treasure.link) ? treasure.link : oldTreasure.link;
      oldTreasure.description =
          treasure.description != null ? treasure.description : oldTreasure.description;
      oldTreasure.modified = Instant.now();
      oldTreasure.update();
      return oldTreasure;
    }, dbExecution);
  }

  @Override public CompletionStage<Stream<Treasure>> list() {
    return supplyAsync(() -> Treasure.find.all().stream(), dbExecution);
  }

  @Override public CompletionStage<Treasure> delete(Long id) {
    return get(id).thenApplyAsync(treasure -> {
      treasure.delete();
      return treasure;
    }, dbExecution);
  }
}
