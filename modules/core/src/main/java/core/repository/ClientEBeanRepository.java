package core.repository;

import com.google.common.base.Strings;
import com.google.common.base.Verify;
import com.google.inject.Singleton;
import core.entity.Client;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.Nonnull;

/**
 * 基于 EBean 的客户端仓库。
 *
 * @author qiang.zhang
 */
@Singleton final class ClientEBeanRepository extends EBeanRepository<Long, Client>
    implements ClientRepository {
  public ClientEBeanRepository() {
    super(Client.class);
  }

  @Nonnull @Override public Optional<Client> find(String name, UUID apikey) {
    Verify.verify(!Strings.isNullOrEmpty(name),
        "name must be not null or empty.");
    Verify.verify(apikey != null,
        "apikey must be not null.");

    return provide(() -> query().where()
        .eq(Client.COL_NAME, name)
        .eq(Client.COL_API_KEY, apikey)
        .findOneOrEmpty());
  }
}
