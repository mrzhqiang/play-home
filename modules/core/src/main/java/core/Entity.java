package core;

/**
 * 实体。
 * <p>
 * 主要是一些必备方法，比如自检、合并，等等。
 *
 * @author mrzhqiang
 */
public interface Entity<T extends Entity> {
  /**
   * 检查，以确保实体状态正常。
   */
  T check();

  /**
   * 合并，对新实体进行有选择地合并，null 字段将被忽略。
   */
  T merge(T newEntity);
}
