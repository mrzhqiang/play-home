package service.account;

import com.google.inject.ImplementedBy;
import core.entity.User;
import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;

/**
 * 用户服务。
 *
 * @author qiang.zhang
 */
@ImplementedBy(UserServiceImpl.class)
public interface UserService {
  /**
   * 取得用户列表。
   * <p>
   * 注意：这个方法可能耗时很长，不建议常用。
   */
  CompletionStage<Stream<User>> list();

  /**
   * 创建用户到数据库。
   */
  CompletionStage<User> create(User user);

  /**
   * 通过主键删除用户。
   */
  CompletionStage<User> delete(Long id);
}
