package core.repository;

import com.google.inject.ImplementedBy;
import core.Repository;
import core.entity.Account;
import core.entity.Token;
import java.util.Optional;

/**
 * @author mrzhqiang
 */
@ImplementedBy(AccountEBeanRepository.class)
public interface AccountRepository extends Repository<Long, Account> {
  default Optional<Token> login(String username, String password) {
    return Optional.ofNullable(username)
        .filter(s -> !s.isEmpty())
        .flatMap(this::searchBy)
        .filter(account -> account.verify(password))
        .map(account -> account.token);
  }

  Optional<Account> searchBy(String username);
}
