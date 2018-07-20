package core.dao;

import core.Cassandra;
import core.entity.Treasure;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;

import static java.util.Objects.*;
import static core.common.CassandraConstant.*;
import static com.google.common.base.Strings.*;
import static com.google.common.base.Preconditions.*;
import static com.datastax.driver.core.querybuilder.QueryBuilder.*;

@Singleton final class TreasureDAOImpl implements TreasureDAO {
  private final CassandraDAO<Treasure> cassandraDAO;

  @Inject TreasureDAOImpl(Cassandra cassandra) {
    this.cassandraDAO = () -> cassandra.getMappingManager().mapper(Treasure.class);
  }

  @Nonnull @Override public List<Treasure> findByName(String name) {
    requireNonNull(name, "name");
    checkArgument(!name.isEmpty(), "name must be not empty.");
    return cassandraDAO.find(
        select().from(KEYSPACE_WOOF, TABLE_TREASURE).where(eq(COMMON_COLUMN_NAME, name))
    );
  }

  @Nonnull @Override public List<Treasure> findAll() {
    return cassandraDAO.find(
        select().from(KEYSPACE_WOOF, TABLE_TREASURE)
    );
  }

  @Override public Treasure add(Treasure entity) {
    requireNonNull(entity, "entity");
    checkArgument(entity.id != null && entity.id > 0, "id");
    checkArgument(!isNullOrEmpty(entity.name), "name");
    return cassandraDAO.add(entity);
  }

  @Override public Treasure remove(Treasure entity) {
    return cassandraDAO.remove(entity);
  }

  @Override public void remove(Object... objects) {
    cassandraDAO.remove(objects);
  }

  @Override public Treasure modify(Treasure entity) {
    return cassandraDAO.modify(entity);
  }

  @Override public Optional<Treasure> find(Object... objects) {
    return cassandraDAO.find(objects);
  }
}
