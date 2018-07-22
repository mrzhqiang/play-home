package service.treasure;

import com.google.inject.ImplementedBy;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;

/**
 * @author mrzhqiang
 */
@ImplementedBy(TreasureServiceImpl.class)
public interface TreasureService {
  CompletionStage<Stream<TreasureResource>> list();

  CompletionStage<TreasureResource> create(TreasureResource resource);

  CompletionStage<TreasureResource> delete(Long id);

  CompletionStage<TreasureResource> update(Long id, TreasureResource resource);

  CompletionStage<Optional<TreasureResource>> get(Long id);

  CompletionStage<Stream<TreasureResource>> get(String name);
}
