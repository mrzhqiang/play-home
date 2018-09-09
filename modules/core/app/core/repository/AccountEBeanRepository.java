package core.repository;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.inject.Singleton;
import core.entity.Account;
import core.entity.Token;
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

  @Nonnull @Override public Optional<Token> login(String username, String password) {
    Preconditions.checkNotNull(username, "Null username.");
    Preconditions.checkNotNull(password, "Null password.");
    Preconditions.checkArgument(        username.length() > 5 && username.length() < 16, "Invalid username.");
    Preconditions.checkArgument(        password.length() > 5 && password.length() < 16, "Invalid password.");
    return Optional.ofNullable(username)
        .filter(s -> Strings.isNullOrEmpty(password))
        .flatMap(s -> dispose(() -> finder.query().where()
            .eq(Account.COL_USERNAME, s)
            .findOneOrEmpty()))
        .filter(account -> {
          String pswd = new String(Base64.getDecoder().decode(password));
          return account.password.equals(pswd);
        })
        .map(account -> account.token);
  }
}
