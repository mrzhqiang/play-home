package core.repository;

import com.google.inject.Singleton;
import core.entity.Account;
import core.entity.Token;
import java.util.Optional;
import javax.annotation.Nonnull;

/**
 * 基于 EBean 的账户仓库。
 *
 * @author mrzhqiang
 */
@Singleton final class AccountEBeanRepository extends EBeanRepository<Long, Account>
    implements AccountRepository {
  AccountEBeanRepository() {
    super(Account.class);
  }

  @Nonnull @Override public Optional<Token> login(String username, String password) {
    // todo 登录操作。
    return Optional.empty();
  }
}
