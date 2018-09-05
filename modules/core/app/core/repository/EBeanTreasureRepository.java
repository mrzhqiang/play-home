package core.repository;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.inject.Singleton;
import core.EBeanRepository;
import core.entity.Treasure;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * 基于 EBean 的宝藏仓库。
 *
 * @author qiang.zhang
 */
@Singleton final class EBeanTreasureRepository extends EBeanRepository<Long, Treasure>
    implements TreasureRepository {
  public EBeanTreasureRepository() {
    super(Treasure.class);
  }

  @Override public Optional<List<Treasure>> search(String name) {
    return Optional.ofNullable(name)
        .filter(s -> !s.isEmpty() && s.length() <= 12)
        .map(s -> dispose(() -> finder.query().where().icontains("name", s).findList()));
  }

  @Override public Optional<Treasure> merge(Treasure entity, Treasure newEntity) {
    Preconditions.checkNotNull(entity);
    Preconditions.checkNotNull(newEntity);
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
