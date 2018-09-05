package core.repository;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.inject.Singleton;
import core.EBeanRepository;
import core.entity.User;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * 基于 EBean 的用户仓库。
 *
 * @author qiang.zhang
 */
@Singleton final class EBeanUserRepository extends EBeanRepository<Long, User>
    implements UserRepository {
  protected EBeanUserRepository() {
    super(User.class);
  }

  @Override public Optional<User> merge(User entity, User newEntity) {
    Preconditions.checkNotNull(entity);
    Preconditions.checkNotNull(newEntity);
    boolean changed = false;
    if (!Strings.isNullOrEmpty(newEntity.nickname)) {
      entity.nickname = newEntity.nickname;
      changed = true;
    }
    if (!Strings.isNullOrEmpty(newEntity.avatar)) {
      entity.avatar = newEntity.avatar;
      changed = true;
    }
    if (newEntity.treasures != null && !entity.treasures.containsAll(newEntity.treasures)) {
      entity.treasures = newEntity.treasures;
      changed = true;
    }
    if (changed) {
      entity.modified = Instant.now();
    }
    return Optional.of(entity);
  }

  @Override public Optional<List<User>> search(String nickname) {
    return Optional.ofNullable(nickname)
        .filter(s -> !s.isEmpty() && s.length() <= 16)
        .map(s -> dispose(() -> finder.query().where().icontains("nickname", s).findList()));
  }
}
