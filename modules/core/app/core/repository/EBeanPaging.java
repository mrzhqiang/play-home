package core.repository;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
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
  private final PagedList<E> pagedList;

  EBeanPaging(PagedList<E> pagedList) {
    Preconditions.checkNotNull(pagedList);
    this.pagedList = pagedList;
    pagedList.loadCount();
  }

  @Override public int total() {
    return pagedList.getTotalCount();
  }

  @Override public int index() {
    return pagedList.getPageIndex();
  }

  @Override public int size() {
    return pagedList.getPageSize();
  }

  @Nonnull @Override public List<E> resource() {
    return pagedList.getList();
  }

  @Override public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("total", total())
        .add("index", index())
        .add("size", size())
        .add("resource", resource())
        .toString();
  }
}
