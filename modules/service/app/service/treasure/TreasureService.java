package service.treasure;

import com.google.inject.ImplementedBy;
import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;
import service.ResourcePaging;

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
  CompletionStage<Stream<TreasureResource>> list();

  /**
   * 创建实体到数据库。
   */
  CompletionStage<TreasureResource> create(TreasureResource resource);

  /**
   * 通过主键删除实体。
   */
  CompletionStage<TreasureResource> delete(Long id);

  /**
   * 根据指定主键，更新实体。
   */
  CompletionStage<TreasureResource> update(Long id, TreasureResource resource);

  /**
   * 根据指定主键，获取实体。
   */
  CompletionStage<TreasureResource> get(Long id);

  /**
   * 根据名字，模糊查询实体，返回分页资源。
   * <p>
   * 注意：这个方法默认只查询前 10 个数据。
   */
  CompletionStage<ResourcePaging<TreasureResource>> search(String name);

  /**
   * 根据名字，页面索引，页面大小，模糊查询实体，返回分页资源。
   */
  CompletionStage<ResourcePaging<TreasureResource>> search(String name, int index, int size);
}
