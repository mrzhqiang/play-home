package core.repository;

import com.google.inject.ImplementedBy;
import core.Repository;
import core.entity.User;
import java.util.List;
import java.util.Optional;

/**
 * @author qiang.zhang
 */
@ImplementedBy(EBeanUserRepository.class)
public interface UserRepository extends Repository<Long, User> {
  Optional<List<User>> search(String nickname);
}
