package service.treasure;

import core.Redis;
import core.common.Treasures;
import core.dao.TreasureDAO;
import core.entity.Treasure;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;
import javax.inject.Inject;
import javax.inject.Singleton;
import service.DatabaseExecutionContext;

import static java.util.Objects.*;
import static core.common.CassandraConstant.*;
import static framework.ApplicationException.*;
import static service.treasure.TreasureResources.*;
import static java.util.concurrent.CompletableFuture.*;

/**
 * @author mrzhqiang
 */
@Singleton final class TreasureServiceImpl implements TreasureService {
  private final Redis redis;
  private final TreasureDAO dao;
  private final DatabaseExecutionContext executionContext;

  @Inject TreasureServiceImpl(Redis redis, TreasureDAO dao,
      DatabaseExecutionContext executionContext) {
    this.redis = redis;
    this.dao = dao;
    this.executionContext = executionContext;
  }

  @Override public CompletionStage<Stream<TreasureResource>> list() {
    return supplyAsync(() -> dao.findAll().stream().map(TreasureResources::from),
        executionContext);
  }

  @Override public CompletionStage<TreasureResource> create(TreasureResource resource) {
    Treasure treasure = to(resource);
    treasure.id = redis.getRedis().hincrBy(KEYSPACE_WOOF, TABLE_TREASURE, 1);
    treasure.created = new Date();
    treasure.updated = new Date();
    return supplyAsync(() -> from(dao.add(treasure)), executionContext);
  }

  @Override public CompletionStage<TreasureResource> delete(Long id) {
    requireNonNull(id, "id");
    return supplyAsync(() -> {
      Optional<Treasure> optional = dao.find(id);
      notFound(optional.isPresent(), "treasure id " + id);
      dao.remove(id);
      return from(optional.get());
    }, executionContext);
  }

  @Override public CompletionStage<TreasureResource> update(Long id, TreasureResource resource) {
    requireNonNull(id, "id");
    TreasureResources.check(resource);
    return supplyAsync(() -> {
      Optional<Treasure> optional = dao.find(id);
      notFound(optional.isPresent(), "treasure id " + id);
      return from(dao.modify(Treasures.merge(optional.get(), to(resource))));
    }, executionContext);
  }

  @Override public CompletionStage<Optional<TreasureResource>> get(Long id) {
    requireNonNull(id, "id");
    return supplyAsync(() -> {
      Optional<Treasure> optionalTreasure = dao.find(id);
      return optionalTreasure.map(TreasureResources::from);
    }, executionContext);
  }

  @Override public CompletionStage<Stream<TreasureResource>> get(String name) {
    requireNonNull(name, "name");
    return supplyAsync(() -> dao.findByName(name).stream().map(TreasureResources::from),
        executionContext);
  }
}
