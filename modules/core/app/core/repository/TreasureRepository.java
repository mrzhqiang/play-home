package core.repository;

import com.google.inject.ImplementedBy;
import core.entity.Treasure;
import java.util.Optional;

/**
 * 宝藏仓库。
 *
 * @author qiang.zhang
 */
@ImplementedBy(TreasureEBeanRepository.class)
public interface TreasureRepository extends Repository<Long, Treasure> {
  Optional<Treasure> findByName(String name);
}
