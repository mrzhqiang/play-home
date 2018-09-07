package core.entity;

/**
 * 实体接口。
 * <p>
 * 主要用来建立实体的自检方法。
 *
 * @author qiang.zhang
 */
public interface Entity {
  /**
   * 内部字段自检。
   *
   * @return true 检测通过；没有 false 的情况，如果检测不成功，直接抛出异常。
   * @throws NullPointerException 字段不能为 Null
   * @throws IllegalStateException 字段不合法
   */
  default boolean checkSelf() {
    return true;
  }
}
