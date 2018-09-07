package core.repository;

import com.google.inject.ImplementedBy;
import core.entity.Account;
import core.entity.Token;
import java.util.Optional;
import javax.annotation.Nonnull;

/**
 * 账户仓库。
 *
 * @author mrzhqiang
 */
@ImplementedBy(AccountEBeanRepository.class)
public interface AccountRepository extends Repository<Long, Account> {
  /**
   * 登录操作。
   *
   * @param username 用户名。
   * @param password 密码，必须是已经编码过的密码。
   * @return 令牌。
   */
  @Nonnull Optional<Token> login(String username, String password);
}
