package core.repository;

import com.google.inject.ImplementedBy;
import core.Repository;
import core.entity.Treasure;
import java.util.List;
import java.util.Optional;

/**
 * 宝藏仓库。
 *
 * @author qiang.zhang
 */
@ImplementedBy(EBeanTreasureRepository.class)
public interface TreasureRepository extends Repository<Long, Treasure> {
  /**
   * 根据名字搜索所有相关的宝藏，这是一个模糊查询，匹配规则是：*name*。
   * <p>
   * TODO 应该实现分页功能。
   */
  Optional<List<Treasure>> search(String name);
}
