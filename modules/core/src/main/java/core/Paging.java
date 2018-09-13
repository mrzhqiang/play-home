package core;

import java.util.List;
import javax.annotation.Nonnull;

/**
 * 实体分页。
 *
 * @author mrzhqiang
 */
public interface Paging<E> {
  /**
   * 实体可获得的总数量。
   */
  int total();

  /**
   * 当前页面的索引位置。
   */
  int index();

  /**
   * 当前页面的大小。
   */
  int size();

  /**
   * 当前页面的资源列表。
   */
  @Nonnull List<E> resources();
}
