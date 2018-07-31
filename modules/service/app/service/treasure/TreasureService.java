package service.treasure;

import com.google.inject.ImplementedBy;
import core.entity.Treasure;
import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;

/**
 * 宝藏服务。
 *
 * @author mrzhqiang
 */
@ImplementedBy(TreasureServiceImpl.class)
public interface TreasureService {
  CompletionStage<Stream<Treasure>> list();

  CompletionStage<Treasure> create(Treasure treasure);

  CompletionStage<Treasure> delete(Long id);

  CompletionStage<Treasure> update(Long id, Treasure resource);

  CompletionStage<Treasure> get(Long id);

  CompletionStage<Treasure> get(String name);
}
