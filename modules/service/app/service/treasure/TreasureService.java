package service.treasure;

import com.google.inject.ImplementedBy;
import core.Paging;
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
  /**
   * 取得宝藏列表。
   * <p>
   * 注意：这个方法可能非常耗时，不应该作为常用方法。
   */
  CompletionStage<Stream<Treasure>> list();

  /**
   * 创建实体到数据库。
   */
  CompletionStage<Treasure> create(Treasure treasure);

  /**
   * 通过主键删除实体。
   */
  CompletionStage<Treasure> delete(Long id);

  /**
   * 根据指定主键，更新实体。
   */
  CompletionStage<Treasure> update(Long id, Treasure resource);

  /**
   * 根据指定主键，获取实体。
   */
  CompletionStage<Treasure> get(Long id);

  /**
   * 根据名字，模糊查询实体，返回分页数据。
   * <p>
   * 注意：这个方法默认只查询前 10 个数据。
   */
  CompletionStage<Paging<Treasure>> search(String name);

  /**
   * 根据名字，页面索引，页面大小，模糊查询实体，返回分页数据。
   */
  CompletionStage<Paging<Treasure>> search(String name, int index, int size);
}
