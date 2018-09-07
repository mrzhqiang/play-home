package core.repository;

import com.google.inject.ImplementedBy;
import core.entity.User;
import javax.annotation.Nonnull;

/**
 * 用户仓库。
 *
 * @author qiang.zhang
 */
@ImplementedBy(UserEBeanRepository.class)
public interface UserRepository extends Repository<Long, User> {
  /**
   * 搜索昵称。
   * <p>
   * 默认搜索前 10 行数据，请注意，这是模糊查询，而非全文匹配。
   *
   * @param nickname 用户昵称。
   * @return 分页数据。
   */
  @Nonnull default Paging<User> search(String nickname) {
    return search(nickname, 0, 10);
  }

  /**
   * 搜索昵称。
   * <p>
   * 允许自定义搜索的起始位置，此次搜索的大小，请注意，这是模糊查询，而非全文匹配。
   *
   * @param nickname 用户昵称。
   * @param from 起始位置，非页面索引。
   * @param size 数据大小。
   * @return 分页数据。
   */
  @Nonnull Paging<User> search(String nickname, int from, int size);
}
