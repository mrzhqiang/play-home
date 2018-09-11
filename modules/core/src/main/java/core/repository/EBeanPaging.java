package core.repository;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import core.Paging;
import core.entity.EBeanModel;
import io.ebean.PagedList;
import java.util.List;
import javax.annotation.Nonnull;

/**
 * 基于 EBean 的实体分页。
 *
 * @author qiang.zhang
 */
final class EBeanPaging<E extends EBeanModel> implements Paging<E> {
  private final int total;
  private final int index;
  private final int size;
  private final List<E> resources;

  EBeanPaging(PagedList<E> pagedList) {
    Preconditions.checkNotNull(pagedList);
    pagedList.loadCount();
    total = pagedList.getTotalCount();
    index = pagedList.getPageIndex();
    size = pagedList.getPageSize();
    resources = pagedList.getList();
  }

  @Override public int total() {
    return total;
  }

  @Override public int index() {
    return index;
  }

  @Override public int size() {
    return size;
  }

  @Nonnull @Override public List<E> resources() {
    return resources;
  }

  @Override public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("资源总计", total())
        .add("页面索引", index())
        .add("页面大小", size())
        .add("页面资源", resources())
        .toString();
  }
}
