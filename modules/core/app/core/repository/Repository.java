package core.repository;

import com.google.common.base.Preconditions;
import core.entity.Entity;
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
public interface Repository<I, E extends Entity> {
  Logger.ALogger logger = Logger.of("core");

  /** 创建实体数据。 */
  @Nonnull Optional<E> create(E entity);

  /** 更新实体数据。 */
  @Nonnull Optional<E> update(E entity);

  /** 获取实体数据。 */
  @Nonnull Optional<E> get(I primaryKey);

  /** 通过指定主键删除实体数据。 */
  @Nonnull Optional<E> deleteBy(I primaryKey);

  /** 直接删除实体数据。 */
  @Nonnull Optional<E> delete(E entity);

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
   * 将实体交给消费者对象去执行，这是一个相对安全的方法。
   *
   * @throws DatabaseException 捕捉执行过程中的所有异常，抛出数据库异常。
   */
  default E execute(E entity, Consumer<E> consumer) {
    Preconditions.checkNotNull(entity);
    Preconditions.checkNotNull(consumer);
    try {
      consumer.accept(entity);
      return entity;
    } catch (Exception e) {
      String message = "CURD execute failed: " + e.getMessage();
      logger.error(message);
      throw new DatabaseException(message);
    }
  }

  /**
   * 处理供应商对象，这是一个相对安全的方法。
   *
   * @throws DatabaseException 捕捉提供过程中的所有异常，抛出数据库异常。
   */
  default <T> T dispose(Supplier<T> supplier) {
    Preconditions.checkNotNull(supplier);
    try {
      return supplier.get();
    } catch (Exception e) {
      String message = "CURD dispose failed: " + e.getMessage();
      logger.error(message);
      throw new DatabaseException(message);
    }
  }
}
