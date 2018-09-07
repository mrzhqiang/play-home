package util;

import java.util.Objects;

/**
 * Entity 助手。
 *
 * @author qiang.zhang
 */
public final class EntityHelper {
  private EntityHelper() {
    throw new AssertionError("No instance.");
  }

  /**
   * 替换指定字段的值，如果替换值合法的话。
   *
   * @param field 指定字段。
   * @param replace 替换值。
   * @param <T> 任意类型。
   * @return 如果替换值不等于指定字段 则使用替换值；否则使用指定字段的值。
   */
  public static <T> T replace(T field, T replace) {
    return Objects.equals(replace, field) ? field : replace;
  }
}
