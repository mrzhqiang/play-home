package service.treasure;

import core.dao.TreasureDAO;
import core.entity.Treasure;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;
import javax.inject.Inject;
import javax.inject.Singleton;
import service.DatabaseExecutionContext;

import static framework.ApplicationException.*;
import static java.util.concurrent.CompletableFuture.*;

/**
 * @author mrzhqiang
 */
@Singleton final class TreasureServiceImpl implements TreasureService {
  private final TreasureDAO dao;
  private final DatabaseExecutionContext dbExecution;

  @Inject TreasureServiceImpl(TreasureDAO dao, DatabaseExecutionContext dbExecution) {
    this.dao = dao;
    this.dbExecution = dbExecution;
  }

  @Override public CompletionStage<Treasure> create(Treasure resource) {
    return supplyAsync(() -> dao.add(resource), dbExecution);
  }

  @Override public CompletionStage<Treasure> get(UUID id) {
    return supplyAsync(() -> {
      Optional<Treasure> optional = dao.find(id);
      notFound(optional.isPresent(), "treasure id:" + id);
      return optional.get();
    }, dbExecution);
  }

  @Override public CompletionStage<Stream<Treasure>> get(String name) {
    return supplyAsync(() -> dao.findByName(name).stream(), dbExecution);
  }

  @Override public CompletionStage<Treasure> update(UUID id, Treasure resource) {
    return supplyAsync(() -> {
      Optional<Treasure> optional = dao.find(id);
      notFound(optional.isPresent(), "treasure id:" + id);
      return dao.update(optional.get(), resource);
    }, dbExecution);
  }

  @Override public CompletionStage<Stream<Treasure>> list() {
    return supplyAsync(() -> dao.findAll().stream(), dbExecution);
  }

  @Override public CompletionStage<Treasure> delete(UUID id) {
    return supplyAsync(() -> {
      Optional<Treasure> optional = dao.find(id);
      notFound(optional.isPresent(), "treasure id: " + id);
      dao.remove(id);
      return optional.get();
    }, dbExecution);
  }
}
