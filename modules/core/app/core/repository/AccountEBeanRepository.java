package core.repository;

import com.google.common.base.Preconditions;
import com.google.inject.Singleton;
import core.entity.Account;
import core.util.Accounts;
import java.util.Base64;
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

  @Nonnull @Override public Optional<Account> find(String username, String password) {
    Preconditions.checkNotNull(username, "Null username.");
    Preconditions.checkNotNull(password, "Null password.");
    Preconditions.checkArgument(Accounts.checkUsername(username), "Invalid username.");
    Preconditions.checkArgument(Accounts.checkPassword(password), "Invalid password.");

    String pswd = new String(Base64.getDecoder().decode(password));
    return provide(() -> finder.query().where()
        .eq(Account.COL_USERNAME, username)
        .eq(Account.COL_PASSWORD, pswd)
        .findOneOrEmpty()
    );
  }
}
