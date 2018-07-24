package service.treasure;

import com.google.common.base.Strings;
import core.dao.TreasureDAO;
import core.entity.Treasure;
import java.util.Optional;
import java.util.UUID;
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

  @Inject TreasureServiceImpl(TreasureDAO dao,
      DatabaseExecutionContext executionContext) {
    this.dao = dao;
    this.executionContext = executionContext;
  }

  @Override public CompletionStage<Stream<TreasureResource>> list() {
    return supplyAsync(() -> dao.findAll().stream().map(this::fromData), executionContext);
  }

  @Override public CompletionStage<TreasureResource> create(TreasureResource resource) {
    return supplyAsync(() -> fromData(dao.add(toData(resource))), executionContext);
  }

  @Override public CompletionStage<TreasureResource> delete(UUID id) {
    return supplyAsync(() -> {
      Optional<Treasure> optional = dao.find(id);
      notFound(optional.isPresent(), "treasure id: " + id);
      dao.remove(id);
      return fromData(optional.get());
    }, executionContext);
  }

  @Override public CompletionStage<TreasureResource> update(UUID id, TreasureResource resource) {
    return supplyAsync(() -> {
      Optional<Treasure> optional = dao.find(id);
      notFound(optional.isPresent(), "treasure id:" + id);
      return fromData(dao.modify(optional.get(), toData(resource)));
    }, executionContext);
  }

  @Override public CompletionStage<TreasureResource> get(UUID id) {
    return supplyAsync(() -> {
      Optional<Treasure> optional = dao.find(id);
      notFound(optional.isPresent(), "treasure id:" + id);
      return fromData(optional.get());
    }, executionContext);
  }

  @Override public CompletionStage<Stream<TreasureResource>> get(String name) {
    return supplyAsync(() -> dao.findByName(name).stream().map(this::fromData), executionContext);
  }

  private TreasureResource fromData(Treasure data) {
    TreasureResource resource = new TreasureResource();
    resource.setId(data.id.toString());
    resource.setName(data.name);
    resource.setDescription(data.description);
    resource.setLink(data.link);
    return resource;
  }

  private Treasure toData(TreasureResource resource) {
    requireNonNull(resource, "resource");
    Treasure treasure = new Treasure();
    treasure.id = UUID.randomUUID();
    treasure.name = resource.getName();
    treasure.description = resource.getDescription();
    treasure.link = resource.getLink();
    return treasure;
  }
}
