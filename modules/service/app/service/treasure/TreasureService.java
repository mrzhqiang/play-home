package service.treasure;

import com.google.inject.ImplementedBy;
import core.entity.Treasure;
import java.util.UUID;
import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;

/**
 * @author mrzhqiang
 */
@ImplementedBy(TreasureServiceImpl.class)
public interface TreasureService {
  CompletionStage<Treasure> create(Treasure resource);

  CompletionStage<Treasure> get(UUID id);

  CompletionStage<Stream<Treasure>> get(String name);

  CompletionStage<Stream<Treasure>> list();

  CompletionStage<Treasure> update(UUID id, Treasure resource);

  CompletionStage<Treasure> delete(UUID id);
}
