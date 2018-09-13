package service.treasure;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import core.repository.TreasureRepository;
import core.util.Treasures;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;
import service.DatabaseExecutionContext;
import service.ResourcePaging;

import static core.exception.ApplicationException.*;
import static java.util.concurrent.CompletableFuture.*;

/**
 * 宝藏服务。
 *
 * @author mrzhqiang
 */
@Singleton final class TreasureServiceImpl implements TreasureService {
  private final TreasureRepository repository;
  private final DatabaseExecutionContext dbExecution;

  @Inject TreasureServiceImpl(TreasureRepository repository, DatabaseExecutionContext dbExecution) {
    this.repository = repository;
    this.dbExecution = dbExecution;
  }

  @Override public CompletionStage<Stream<TreasureResource>> list() {
    return supplyAsync(
        () -> repository.list().stream().map(TreasureResource::of),
        dbExecution);
  }

  @Override public CompletionStage<TreasureResource> create(TreasureResource treasure) {
    return supplyAsync(
        () -> repository.save(treasure.toTreasure())
            .map(TreasureResource::of)
            .orElseThrow(() -> badRequest("case: " + treasure)),
        dbExecution);
  }

  @Override public CompletionStage<TreasureResource> delete(Long id) {
    return supplyAsync(
        () -> repository.delete(id)
            .map(TreasureResource::of)
            .orElseThrow(() -> notFound("id: " + id)),
        dbExecution);
  }

  @Override public CompletionStage<TreasureResource> update(Long id, TreasureResource resource) {
    return supplyAsync(
        () -> Optional.ofNullable(id)
            .flatMap(repository::get)
            .map(treasure -> Treasures.merge(treasure, resource.toTreasure()))
            .flatMap(repository::save)
            .map(TreasureResource::of)
            .orElseThrow(() -> notFound("id: " + id)),
        dbExecution);
  }

  @Override public CompletionStage<TreasureResource> get(Long id) {
    return supplyAsync(
        () -> repository.get(id)
            .map(TreasureResource::of)
            .orElseThrow(() -> notFound("id: " + id)),
        dbExecution);
  }

  @Override public CompletionStage<ResourcePaging<TreasureResource>> search(String name) {
    return supplyAsync(
        () -> ResourcePaging.convert(repository.search(name), TreasureResource::of)
        , dbExecution);
  }

  @Override public CompletionStage<ResourcePaging<TreasureResource>> search(String name, int index,
      int size) {
    return supplyAsync(
        () -> ResourcePaging.convert(repository.search(name, index, size), TreasureResource::of)
        , dbExecution);
  }
}
