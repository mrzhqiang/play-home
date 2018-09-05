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

  /** 从数据库中创建实体数据。 */
  Optional<E> create(E entity);

  /** 通过指定主键更新实体数据。 */
  Optional<E> update(I primaryKey, E entity);

  /** 通过指定主键获取实体数据。 */
  Optional<E> get(I primaryKey);

  /** 通过指定主键删除实体数据。 */
  Optional<E> delete(I primaryKey);

  /**
   * 获取此仓库的所有实体数据。
   * <p>
   * TODO 分页功能。
   */
  @Nonnull List<E> list();

  /** 合并两个实体数据，null 值将被忽略。 */
  Optional<E> merge(E entity, E newEntity);

  /**
   * 将实体交给消费者对象去执行，这是一个相对安全的方法。
   *
   * @throws DatabaseException 捕捉执行过程中的所有异常，抛出数据库异常。
   */
  default void execute(E entity, Consumer<E> consumer) {
    Preconditions.checkNotNull(entity);
    Preconditions.checkNotNull(consumer);
    try {
      consumer.accept(entity);
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
