package core.repository;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.inject.Singleton;
import core.EBeanRepository;
import core.entity.Client;
import java.time.Instant;
import java.util.Optional;

/**
 * 基于 EBean 的客户端仓库。
 *
 * @author qiang.zhang
 */
@Singleton final class EBeanClientRepository extends EBeanRepository<Long, Client>
    implements ClientRepository {
  public EBeanClientRepository() {
    super(Client.class);
  }

  @Override public Optional<Client> merge(Client entity, Client newEntity) {
    Preconditions.checkNotNull(entity);
    Preconditions.checkNotNull(newEntity);
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
    Preconditions.checkNotNull(username);
    Preconditions.checkNotNull(password);
    return dispose(() -> finder.query().where()
        .eq("name", username)
        .eq("apikey", password)
        .findOneOrEmpty());
  }
}
