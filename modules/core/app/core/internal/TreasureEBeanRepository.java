package core.internal;

import com.google.inject.Singleton;
import core.entity.Treasure;
import core.repository.TreasureRepository;
import io.ebean.Finder;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nonnull;

/**
 * 基于 EBean 的宝藏仓库。
 *
 * @author qiang.zhang
 */
@Singleton final class TreasureEBeanRepository implements EBeanRepository<Long, Treasure>,
    TreasureRepository {

  @Override public Optional<List<Treasure>> search(String name) {
    return Optional.ofNullable(name).map(s -> {
      try {
        return finder().query().where().icontains("name", s).findList();
      } catch (Exception e) {
        logger("find by name", s, e);
        throw new RuntimeException("find by name error: " + e.getMessage());
      }
    });
  }

  @Nonnull @Override public Finder<Long, Treasure> finder() {
    return null;
  }

  @Nonnull @Override public Treasure merge(Treasure oldEntity, Treasure newEntity) {
    return null;
  }
}
