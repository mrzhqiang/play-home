package core.repository;

import com.google.inject.ImplementedBy;
import core.Paging;
import core.Repository;
import core.entity.Treasure;
import javax.annotation.Nonnull;

/**
 * 宝藏仓库。
 *
 * @author qiang.zhang
 */
@ImplementedBy(TreasureEBeanRepository.class)
public interface TreasureRepository extends Repository<Long, Treasure> {
  /**
   * 根据名字搜索所有相关的宝藏，这是一个模糊查询，匹配规则是：*name*。
   * <p>
   * 注意：默认从 0 开始搜索前 10 条数据。
   *
   * @param name 宝藏名字。
   * @return 分页数据。
   */
  @Nonnull default Paging<Treasure> search(String name) {
    return search(name, 0, 10);
  }

  /**
   * 根据名字搜索所有相关的宝藏，这是一个模糊查询，匹配规则是：*name*。
   *
   * @param name 宝藏名字。
   * @param from 起始位置，非页面索引。
   * @param size 数据大小。
   * @return 分页数据。
   */
  @Nonnull Paging<Treasure> search(String name, int from, int size);
}
