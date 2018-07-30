package service.treasure;

import com.google.inject.ImplementedBy;
import core.entity.Treasure;
import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;

/**
 * @author mrzhqiang
 */
@ImplementedBy(TreasureServiceImpl.class)
public interface TreasureService {
  CompletionStage<Treasure> create(String name, String link, String description);

  CompletionStage<Treasure> get(Long id);

  CompletionStage<Treasure> get(String name);

  CompletionStage<Stream<Treasure>> list();

  CompletionStage<Treasure> update(Long id, Treasure resource);

  CompletionStage<Treasure> delete(Long id);
}
