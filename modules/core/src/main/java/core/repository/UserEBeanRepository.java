package core.repository;

import com.google.common.base.Strings;
import com.google.common.base.Verify;
import com.google.inject.Singleton;
import core.Paging;
import core.entity.User;
import io.ebean.PagedList;
import javax.annotation.Nonnull;

/**
 * 基于 EBean 的用户仓库。
 *
 * @author qiang.zhang
 */
@Singleton final class UserEBeanRepository extends EBeanRepository<Long, User>
    implements UserRepository {
  UserEBeanRepository() {
    super(User.class);
  }

  @Nonnull @Override public Paging<User> search(String nickname, int index, int size) {
    Verify.verify(!Strings.isNullOrEmpty(nickname),
        "nickname must be not null or empty.");

    int firstRow = computeFirstRow(index, size);
    int maxRows = computeMaxRows(size);
    return provide(() -> {
      PagedList<User> pagedList = query().where()
          .icontains(User.COL_NICKNAME, nickname)
          .setFirstRow(firstRow)
          .setMaxRows(maxRows)
          .findPagedList();
      return new EBeanPaging<>(pagedList);
    });
  }
}
