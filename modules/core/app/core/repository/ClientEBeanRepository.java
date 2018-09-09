package core.repository;

import com.google.common.base.Preconditions;
import com.google.inject.Singleton;
import core.entity.Client;
import core.util.Clients;
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
    Preconditions.checkNotNull(name, "Null name.");
    Preconditions.checkState(Clients.checkName(name), "Invalid name.");
    Preconditions.checkNotNull(apikey, "Null apikey.");

    return provide(() -> finder.query().where()
        .eq("name", name)
        .eq("apikey", apikey)
        .findOneOrEmpty());
  }
}
