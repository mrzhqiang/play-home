package service.treasure;

import com.google.inject.ImplementedBy;
import java.util.UUID;
import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;

/**
 * @author mrzhqiang
 */
@ImplementedBy(TreasureServiceImpl.class)
public interface TreasureService {
  CompletionStage<Stream<TreasureResource>> list();

  CompletionStage<TreasureResource> create(TreasureResource resource);

  CompletionStage<TreasureResource> delete(UUID id);

  CompletionStage<TreasureResource> update(UUID id, TreasureResource resource);

  CompletionStage<TreasureResource> get(UUID id);

  CompletionStage<Stream<TreasureResource>> get(String name);
}
