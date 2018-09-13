package core;

import com.google.common.base.Preconditions;
import core.exception.DatabaseException;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;
import javax.annotation.Nonnull;
import play.Logger;

/**
 * 实体仓库。
 * <p>
 * 定义数据库的 CURD 操作。
 * <p>
 * 此接口应保持兼容性。无论是 CQL 还是 SQL 数据库，都可以进行 CURD 操作。
 *
 * @author qiang.zhang
 */
public interface Repository<I, E> {
  Logger.ALogger logger = Logger.of("core");

  /**
   * 保存实体数据。
   * <p>
   * 不存在，插入；存在，更新。
   */
  Optional<E> save(E entity);

  /**
   * 通过主键删除实体数据。
   */
  Optional<E> delete(I primaryKey);

  /**
   * 获取实体数据。
   */
  @Nonnull Optional<E> get(I primaryKey);

  /**
   * 获取此仓库的实体数据列表，从指定行数开始，获取固定大小的数据。
   */
  @Nonnull Paging<E> list(int from, int size);

  /**
   * 获取此仓库的所有数据。
   * <p>
   * 注意：如果数量太多，有可能导致查询超时，慎用。
   */
  @Nonnull List<E> list();

  /**
   * 将实体交给消费者执行，这是一个相对安全的方法。
   *
   * @throws DatabaseException 捕捉执行过程中的所有异常，抛出数据库异常。
   */
  default void execute(E entity, Consumer<E> consumer) {
    Preconditions.checkNotNull(consumer);
    try {
      Optional.ofNullable(entity).ifPresent(consumer);
    } catch (Exception e) {
      String message = "Repository execute failed: " + e.getMessage();
      logger.error(message);
      throw new DatabaseException(message);
    }
  }

  /**
   * 通过供应商来提供所需的对象，这是一个相对安全的方法。
   *
   * @throws DatabaseException 捕捉提供过程中的所有异常，抛出数据库异常。
   */
  default <T> T provide(Supplier<T> supplier) {
    Preconditions.checkNotNull(supplier);
    try {
      return supplier.get();
    } catch (Exception e) {
      String message = "Repository dispose failed: " + e.getMessage();
      logger.error(message);
      throw new DatabaseException(message);
    }
  }

  /**
   * 通过页面索引，计算当前页面第一行的序号。
   */
  default int firstRowByIndex(int from, int size) {
    if (from < 1) {
      return 1;
    }
    return (from - 1) * size + 1;
  }

  /**
   * 通过传入的页面大小，返回一页的最大行数。
   * <p>
   * 注意：最大行数最小为 10。
   */
  default int maxRowsBySize(int size) {
    return size < 10 ? 10 : size;
  }
}
