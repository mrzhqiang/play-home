package service.treasure;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import core.Paging;
import core.entity.Treasure;
import core.repository.TreasureRepository;
import core.util.Treasures;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;
import service.DatabaseExecutionContext;

import static core.exception.ApplicationException.*;
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
        () -> repository.save(treasure).orElseThrow(() -> badRequest("case: " + treasure)),
        dbExecution);
  }

  @Override public CompletionStage<Treasure> delete(Long id) {
    return supplyAsync(
        () -> repository.delete(id).orElseThrow(() -> notFound("id: " + id)),
        dbExecution);
  }

  @Override public CompletionStage<Treasure> update(Long id, Treasure entity) {
    return supplyAsync(
        () -> Optional.ofNullable(id)
            .flatMap(repository::get)
            .map(treasure1 -> Treasures.merge(treasure1, entity))
            .flatMap(repository::save)
            .orElseThrow(() -> notFound("id: " + id)),
        dbExecution);
  }

  @Override public CompletionStage<Treasure> get(Long id) {
    return supplyAsync(
        () -> repository.get(id).orElseThrow(() -> notFound("id: " + id)),
        dbExecution);
  }

  @Override public CompletionStage<Paging<Treasure>> search(String name) {
    return supplyAsync(
        () -> repository.search(name)
        , dbExecution);
  }

  @Override public CompletionStage<Paging<Treasure>> search(String name, int index, int size) {
    return supplyAsync(
        () -> repository.search(name, index, size)
        , dbExecution);
  }
}
