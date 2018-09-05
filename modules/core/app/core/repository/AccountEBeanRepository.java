package core.repository;

import com.google.inject.Singleton;
import core.EBeanRepository;
import core.entity.Account;
import java.util.Optional;

/**
 * @author mrzhqiang
 */
@Singleton
public final class AccountEBeanRepository extends EBeanRepository<Long, Account>
    implements AccountRepository {
  protected AccountEBeanRepository() {
    super(Account.class);
  }

  @Override public Optional<Account> searchBy(String username) {
    return Optional.empty();
  }

  @Override public Optional<Account> merge(Account entity, Account newEntity) {
    return Optional.empty();
  }
}
