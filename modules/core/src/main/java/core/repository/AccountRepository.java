package core.repository;

import com.google.inject.ImplementedBy;
import core.Repository;
import core.entity.Account;
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
   * 查找账号密码。
   *
   * @param username 用户名。
   * @param password 密码，内部自动加密处理。
   */
  @Nonnull Optional<Account> find(String username, String password);
}
