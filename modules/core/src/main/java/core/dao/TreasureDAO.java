package core.dao;

import core.entity.Treasure;
import com.google.inject.ImplementedBy;
import java.util.List;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;

/**
 * 宝藏数据映射接口.
 *
 * @author mrzhqiang
 */
@ImplementedBy(TreasureDAOImpl.class)
public interface TreasureDAO extends CassandraDAO<Treasure> {
  /**
   * 通过 name 字段查询所有的宝藏列表。
   *
   * @param name 宝藏的名称。
   * @return 宝藏列表。
   */
  @Nonnull
  @CheckReturnValue
  List<Treasure> findByName(String name);

  /**
   * 查询数据库中所有的宝藏。
   *
   * @return 宝藏列表。
   */
  @Nonnull
  @CheckReturnValue
  List<Treasure> findAll();
}
