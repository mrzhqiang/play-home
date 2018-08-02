package core.repository;

import com.google.inject.ImplementedBy;
import core.Repository;
import core.entity.Treasure;
import core.internal.TreasureEBeanRepository;
import java.util.List;
import java.util.Optional;

/**
 * 宝藏仓库。
 *
 * @author qiang.zhang
 */
@ImplementedBy(TreasureEBeanRepository.class)
public interface TreasureRepository extends Repository<Long, Treasure> {
  Optional<List<Treasure>> search(String name);
}
