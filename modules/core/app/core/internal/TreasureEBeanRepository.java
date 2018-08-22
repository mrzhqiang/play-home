package core.internal;

import com.google.common.base.Strings;
import com.google.inject.Singleton;
import core.EBeanRepository;
import core.entity.Treasure;
import core.repository.TreasureRepository;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 基于 EBean 的宝藏仓库。
 *
 * @author qiang.zhang
 */
@Singleton public final class TreasureEBeanRepository extends EBeanRepository<Long, Treasure>
    implements TreasureRepository {
  public TreasureEBeanRepository() {
    super(Treasure.class);
  }

  @Override public Optional<List<Treasure>> search(String name) {
    return Optional.ofNullable(name)
        .map(s -> dispose(() -> finder.query().where().icontains("name", s).findList()));
  }

  @Override public Optional<Treasure> merge(Treasure entity, Treasure newEntity) {
    Objects.requireNonNull(entity);
    Objects.requireNonNull(newEntity);
    boolean changed = false;
    if (!Strings.isNullOrEmpty(newEntity.name)) {
      entity.name = newEntity.name;
      changed = true;
    }
    if (!Strings.isNullOrEmpty(newEntity.link)) {
      entity.link = newEntity.link;
      changed = true;
    }
    if (changed) {
      entity.modified = Instant.now();
    }
    return Optional.of(entity);
  }
}
