package service.treasure;

import core.common.Treasures;
import core.dao.TreasureDAO;
import core.entity.Treasure;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;
import javax.inject.Inject;
import javax.inject.Singleton;
import service.DatabaseExecutionContext;

import static java.util.Objects.*;
import static framework.ApplicationException.*;
import static java.util.concurrent.CompletableFuture.*;

/**
 * @author mrzhqiang
 */
@Singleton final class TreasureServiceImpl implements TreasureService {
  private final TreasureDAO dao;
  private final DatabaseExecutionContext executionContext;

  @Inject TreasureServiceImpl(TreasureDAO dao, DatabaseExecutionContext executionContext) {
    this.dao = dao;
    this.executionContext = executionContext;
  }

  @Override public CompletionStage<Stream<Treasure>> list() {
    return supplyAsync(() -> dao.findAll().stream(), executionContext);
  }

  @Override public CompletionStage<Treasure> create(Treasure treasure) {
    Treasures.check(treasure);
    return supplyAsync(() -> dao.add(treasure), executionContext);
  }

  @Override public CompletionStage<Treasure> delete(Long id) {
    requireNonNull(id, "id");
    return supplyAsync(() -> {
      Optional<Treasure> optional = dao.find(id);
      notFound(optional.isPresent(), "treasure id " + id);
      dao.remove(id);
      return optional.get();
    }, executionContext);
  }

  @Override public CompletionStage<Treasure> update(Long id, Treasure treasure) {
    requireNonNull(id, "id");
    Treasures.check(treasure);
    return supplyAsync(() -> {
      Optional<Treasure> optional = dao.find(id);
      notFound(optional.isPresent(), "treasure id " + id);
      return dao.modify(Treasures.merge(optional.get(), treasure));
    }, executionContext);
  }

  @Override public CompletionStage<Optional<Treasure>> get(Long id) {
    requireNonNull(id, "id");
    return supplyAsync(() -> dao.find(id), executionContext);
  }

  @Override public CompletionStage<Stream<Treasure>> get(String name) {
    requireNonNull(name, "name");
    return supplyAsync(() -> dao.findByName(name).stream(), executionContext);
  }
}
