package core;

import com.datastax.driver.mapping.Mapper;
import com.google.inject.ImplementedBy;
import javax.annotation.Nullable;

/**
 * Cassandra 驱动接口。
 *
 * @author mrzhqiang
 */
@ImplementedBy(StandaloneCassandra.class)
public interface Cassandra {
  /**
   * 数据结构初始化。
   */
  void init();

  /**
   * 提供指定类型的映射器。
   *
   * 注意：如果它返回 null 值，则表示没有可用的 Cassandra cluster 实例。
   */
  @Nullable <T> Mapper<T> mapper(Class<T> clazz);
}
