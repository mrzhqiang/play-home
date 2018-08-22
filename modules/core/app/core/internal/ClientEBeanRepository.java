package core.internal;

import com.google.common.base.Strings;
import com.google.inject.Singleton;
import core.EBeanRepository;
import core.entity.Client;
import core.repository.ClientRepository;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;

/**
 * 基于 EBean 的客户端仓库。
 *
 * @author qiang.zhang
 */
@Singleton
public final class ClientEBeanRepository extends EBeanRepository<Long, Client>
    implements ClientRepository {
  public ClientEBeanRepository() {
    super(Client.class);
  }

  @Override public Optional<Client> merge(Client entity, Client newEntity) {
    Objects.requireNonNull(entity);
    Objects.requireNonNull(newEntity);
    boolean changed = false;
    if (!Strings.isNullOrEmpty(newEntity.name)) {
      entity.name = newEntity.name;
      changed = true;
    }
    if (newEntity.apikey != null) {
      entity.apikey = newEntity.apikey;
      changed = true;
    }
    if (changed) {
      entity.modified = Instant.now();
    }
    return Optional.of(entity);
  }

  @Override public Optional<Client> verify(String username, String password) {
    Objects.requireNonNull(username);
    Objects.requireNonNull(password);
    return dispose(() -> finder.query().where()
        .eq("name", username)
        .eq("apikey", password)
        .findOneOrEmpty());
  }
}
