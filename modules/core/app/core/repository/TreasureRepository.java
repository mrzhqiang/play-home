package core.repository;

import com.google.inject.ImplementedBy;
import core.Paging;
import core.Repository;
import core.entity.Treasure;
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
   * 利用接口实现分页功能，比类包装更灵活。
   */
  Optional<Paging<Treasure>> search(String name, int firstRow, int maxRows);
}
