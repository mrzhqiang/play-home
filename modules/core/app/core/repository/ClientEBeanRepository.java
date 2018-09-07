package core.repository;

import com.google.common.base.Preconditions;
import com.google.inject.Singleton;
import core.entity.Client;
import java.util.Optional;
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

  @Nonnull @Override public Optional<Client> find(String name, String apikey) {
    Preconditions.checkNotNull(name);
    Preconditions.checkState(!name.isEmpty() && name.length() <= 24);
    Preconditions.checkNotNull(apikey);

    return dispose(() -> finder.query().where()
        .eq("name", name)
        .eq("apikey", apikey)
        .findOneOrEmpty());
  }
}
