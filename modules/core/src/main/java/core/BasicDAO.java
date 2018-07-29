package core;

import java.util.List;
import java.util.Optional;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;

/**
 * 基本的实体对象映射器。
 *
 * @author mrzhqiang
 */
public interface BasicDAO<T> {
  /**
   * 检查实体状态。
   */
  @Nonnull
  T check(T entity);

  /**
   * 添加实体。
   */
  boolean add(T entity);

  /**
   * 根据主键寻找。
   */
  @CheckReturnValue
  Optional<T> find(Object... objects);

  /**
   * 合并新旧实体。
   */
  @Nonnull
  T merge(T oldEntity, T newEntity);

  /**
   * 更新实体。
   */
  boolean update(T entity, Object... objects);

  /**
   * 移除实体。
   */
  boolean remove(T entity);

  /**
   * 根据主键移除。
   */
  boolean remove(Object... objects);

  /**
   * 执行查询语句。
   */
  @Nonnull
  @CheckReturnValue
  List<T> execute(String query);
}
