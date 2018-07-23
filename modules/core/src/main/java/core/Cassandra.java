package core;

import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.google.inject.ImplementedBy;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;

/**
 * Cassandra 驱动接口。
 *
 * @author mrzhqiang
 */
@ImplementedBy(StandaloneCassandra.class)
public interface Cassandra {
  /**
   * 数据结构初始化。
   * <p>
   * 在应用启动之前，通过其他程序调用一次，以保证数据库正常可用。
   */
  void init();

  /**
   * 提供指定类型的映射器。
   * <p>
   * 只应该在 core 模块中的 DAO 层使用。
   */
  @Nonnull
  @CheckReturnValue <T> Mapper<T> mapper(Class<T> clazz);

  /**
   * 获得 Cassandra 会话。
   * <p>
   * 应当尽量避免使用这个实例，除了比较复杂的查询语句。
   */
  @Nonnull
  @CheckReturnValue
  Session getSession();
}
