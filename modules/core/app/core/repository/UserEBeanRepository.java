package core.repository;

import com.google.common.base.Preconditions;
import com.google.inject.Singleton;
import core.Paging;
import core.entity.User;
import core.util.Users;
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
    Preconditions.checkNotNull(nickname, "Null nickname.");
    Preconditions.checkArgument(Users.checkNickname(nickname), "Invalid nickname.");

    int firstRow = from < 0 ? 0 : from;
    int maxRows = size < 10 ? 10 : size;
    return provide(() -> {
      PagedList<User> pagedList = finder.query().where()
          .icontains(User.COL_NICKNAME, nickname)
          .setFirstRow(firstRow)
          .setMaxRows(maxRows)
          .findPagedList();
      return new EBeanPaging<>(pagedList);
    });
  }
}
