package core.dao;

import core.Entity;
import java.util.Optional;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;

/**
 * 最基础的实体映射器。
 * <p>
 * 仅包含与数据库驱动无关的 CURD 方法。
 *
 * @param <T> 数据库实体类。
 * @author mrzhqiang
 */
interface BasicDAO<T extends Entity<T>> {
  /**
   * 添加实体到数据库中。
   *
   * @param entity 实体类。
   * @return 传入的实体类。
   */
  @Nonnull
  T add(T entity);

  /**
   * 从数据库中移除实体。
   *
   * @param entity 实体类。
   * @return 传入的实体类。
   */
  @Nonnull
  T remove(T entity);

  /**
   * 根据提供的信息，从数据库中移除实体。
   * <p>
   * 一般来说，是根据数据库的主键进行移除。
   *
   * @param objects 主键对象。
   */
  void remove(Object... objects);

  /**
   * 修改实体。
   * <p>
   * 传入旧实体的原因在于，新实体只需要传部分需要修改的字段，因此必须检查 null 值。
   *
   * @param oldEntity 旧的实体，从数据库中取出。
   * @param newEntity 新的实体，从请求体中取出。
   * @return 全新的实体对象，以旧实体为蓝本，提取新实体的有效字段并更新。
   */
  @Nonnull
  T modify(T oldEntity, T newEntity);

  /**
   * 根据提供的信息，从数据库中查询实体。
   * <p>
   * 一般来说，是根据数据库的主键进行查询。
   *
   * @param objects 主键对象。
   * @return 可选的实体容器。
   */
  @CheckReturnValue
  Optional<T> find(Object... objects);
}
