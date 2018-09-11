package core;

/**
 * 实体。
 *
 * @author qiang.zhang
 */
public interface Entity {
  /**
   * 内部自检。
   *
   * @return true 检测通过；如果检测不成功，直接抛出异常。
   * @throws NullPointerException Null 字段
   * @throws IllegalStateException 不合法字段
   */
  boolean checkSelf();
}
