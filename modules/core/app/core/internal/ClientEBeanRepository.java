package core.internal;

import com.google.inject.Singleton;
import core.EBeanRepository;
import core.entity.Client;
import core.repository.ClientRepository;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nonnull;

import static com.google.common.base.Strings.isNullOrEmpty;

/**
 * 客户端基于 EBean 的仓库。
 *
 * @author qiang.zhang
 */
@Singleton final class ClientEBeanRepository implements ClientRepository {
  private final EBeanRepository<Long, Client> repository =
      new EBeanRepository<Long, Client>(Client.class) {
        @Nonnull @Override Client merge(Client oldEntity, Client newEntity) {
          Optional.ofNullable(newEntity).ifPresent(client -> {
            oldEntity.name = isNullOrEmpty(newEntity.name) ? oldEntity.name : newEntity.name;
          });
          return oldEntity;
        }
      };

  @Override public Optional<Client> verify(String username, String password) {
    return Optional.ofNullable(username)
        .filter(s -> Optional.ofNullable(password).isPresent())
        .flatMap(s -> repository.find.query().where()
            .eq("name", s)
            .eq("apikey", password)
            .findOneOrEmpty());
  }

  @Nonnull @Override public List<Client> list() {
    return repository.list();
  }

  @Nonnull @Override public Client create(Client entity) {
    return repository.create(entity);
  }

  @Override public Optional<Client> delete(Long primaryKey) {
    return repository.delete(primaryKey);
  }

  @Override public Optional<Client> update(Long primaryKey, Client entity) {
    return repository.update(primaryKey, entity);
  }

  @Override public Optional<Client> get(Long primaryKey) {
    return repository.get(primaryKey);
  }
}
