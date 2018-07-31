package service.treasure;

import core.entity.Treasure;
import core.repository.TreasureRepository;
import java.util.Objects;
import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;
import javax.inject.Inject;
import javax.inject.Singleton;
import service.DatabaseExecutionContext;

import static core.ApplicationException.*;
import static java.util.concurrent.CompletableFuture.*;

/**
 * 宝藏服务的实现。
 *
 * @author mrzhqiang
 */
@Singleton final class TreasureServiceImpl implements TreasureService {
  private final TreasureRepository repository;
  private final DatabaseExecutionContext dbExecution;

  @Inject TreasureServiceImpl(TreasureRepository repository,
      DatabaseExecutionContext dbExecution) {
    this.repository = repository;
    this.dbExecution = dbExecution;
  }

  @Override public CompletionStage<Stream<Treasure>> list() {
    return supplyAsync(
        () -> repository.list().stream(),
        dbExecution);
  }

  @Override public CompletionStage<Treasure> create(Treasure treasure) {
    return supplyAsync(
        () -> repository.create(treasure),
        dbExecution);
  }

  @Override public CompletionStage<Treasure> delete(Long id) {
    return supplyAsync(
        () -> repository.delete(id).orElseThrow(() -> notFound("id: " + id)),
        dbExecution);
  }

  @Override public CompletionStage<Treasure> update(Long id, Treasure treasure) {
    return supplyAsync(
        () -> repository.update(id, treasure).orElseThrow(() -> notFound("id: " + id)),
        dbExecution);
  }

  @Override public CompletionStage<Treasure> get(Long id) {
    return supplyAsync(
        () -> repository.get(id).orElseThrow(() -> notFound("id: " + id)),
        dbExecution);
  }

  @Override public CompletionStage<Treasure> get(String name) {
    return supplyAsync(
        () -> repository.findByName(name)
            .orElseThrow(() -> badRequest("get by name failed."))
        , dbExecution);
  }
}
