package service.treasure;

import com.google.inject.ImplementedBy;
import core.entity.Treasure;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;

/**
 * @author mrzhqiang
 */
@ImplementedBy(TreasureServiceImpl.class)
public interface TreasureService {
  CompletionStage<Stream<Treasure>> list();

  CompletionStage<Treasure> create(Treasure treasure);

  CompletionStage<Treasure> delete(Long id);

  CompletionStage<Treasure> update(Long id, Treasure treasure);

  CompletionStage<Optional<Treasure>> get(Long id);

  CompletionStage<Stream<Treasure>> get(String name);
}
