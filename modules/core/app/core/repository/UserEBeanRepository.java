package core.repository;

import com.google.common.base.Preconditions;
import com.google.inject.Singleton;
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

  @Nonnull @Override public Paging<User> search(String nickname, int from, int size) {
    Preconditions.checkNotNull(nickname);
    Preconditions.checkArgument(!nickname.isEmpty() && nickname.length() <= 16);

    int firstRow = from < 0 ? 0 : from;
    int maxRows = size < 10 ? 10 : size;
    PagedList<User> pagedList = dispose(() ->
        finder.query().where()
            .icontains(User.COL_NICKNAME, nickname)
            .setFirstRow(firstRow)
            .setMaxRows(maxRows)
            .findPagedList());
    return new EBeanPaging<>(pagedList);
  }
}
