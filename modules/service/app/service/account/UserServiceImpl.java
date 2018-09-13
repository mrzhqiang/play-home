package service.account;

/**
 * @author qiang.zhang
 */

import com.google.inject.Singleton;
import core.entity.User;
import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;

@Singleton final class UserServiceImpl implements UserService{
  @Override public CompletionStage<Stream<User>> list() {
    return null;
  }

  @Override public CompletionStage<User> create(User user) {
    return null;
  }

  @Override public CompletionStage<User> delete(Long id) {
    return null;
  }
}
