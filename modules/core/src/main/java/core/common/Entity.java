package core.common;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;

/**
 * 数据库实体接口。
 * <p>
 * 主要是针对实体的通用方法，比如自检、合并，等等。
 *
 * @author mrzhqiang
 */
public interface Entity<T> {
  /**
   * 检查，以确保实体状态正常。
   *
   * @return 返回实体本身。
   */
  @Nonnull
  T check();

  /**
   * 合并，对实体参数进行有选择地合并，null 值将被忽略。
   *
   * @param valueEntity 实体参数。
   * @return 返回实体本身。
   */
  @Nonnull
  @CheckReturnValue
  T merge(T valueEntity);
}
