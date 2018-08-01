package core.repository;

import com.google.inject.Singleton;
import core.entity.Treasure;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nonnull;

import static com.google.common.base.Strings.isNullOrEmpty;

/**
 * 基于 EBean 的宝藏仓库。
 *
 * @author qiang.zhang
 */
@Singleton final class TreasureEBeanRepository implements TreasureRepository {
  private final EBeanRepository<Long, Treasure> repository =
      new EBeanRepository<Long, Treasure>(Treasure.class) {
        @Nonnull @Override public Treasure merge(Treasure oldEntity, Treasure newEntity) {
          if (newEntity != null) {
            oldEntity.name = isNullOrEmpty(newEntity.name) ? oldEntity.name : newEntity.name;
            oldEntity.link = isNullOrEmpty(newEntity.link) ? oldEntity.link : newEntity.link;
            oldEntity.description = isNullOrEmpty(newEntity.description) ? oldEntity.description
                : newEntity.description;
            oldEntity.modified = Instant.now();
          }
          return oldEntity;
        }
      };

  @Nonnull @Override public List<Treasure> list() {
    return repository.list();
  }

  @Nonnull @Override public Treasure create(Treasure entity) {
    return repository.create(entity);
  }

  @Override public Optional<Treasure> delete(Long id) {
    return repository.delete(id);
  }

  @Override public Optional<Treasure> update(Long id, Treasure entity) {
    return repository.update(id, entity);
  }

  @Override public Optional<Treasure> get(Long id) {
    return repository.get(id);
  }

  @Override public Optional<Treasure> findByName(String name) {
    return Optional.ofNullable(name).map(s -> {
      try {
        return repository.find.query().where().eq("name", s).findOne();
      } catch (Exception e) {
        repository.logger("find by name", s, e);
        throw new RuntimeException("find by name error: " + e.getMessage());
      }
    });
  }
}
