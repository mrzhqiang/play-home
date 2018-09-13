package core.repository;

import com.google.common.base.Strings;
import com.google.common.base.Verify;
import com.google.inject.Singleton;
import core.entity.Account;
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
    Verify.verify(Accounts.checkUsername(username), "invalid username: %s", username);
    Verify.verify(!Strings.isNullOrEmpty(password), "null or empty password: %s", password);
    String pswd = Base64.getEncoder().encodeToString(password.getBytes());
    Verify.verify(Accounts.checkPassword(pswd), "invalid password: %s", password);

    return provide(() -> finder.query().where()
        .eq(Account.COL_USERNAME, username)
        .eq(Account.COL_PASSWORD, pswd)
        .findOneOrEmpty()
    );
  }
}
