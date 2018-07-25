package core.dao;

import com.datastax.driver.mapping.Mapper;
import core.Cassandra;
import core.entity.Treasure;
import java.util.List;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;

import static core.common.CassandraConstant.*;
import static com.google.common.base.Preconditions.*;
import static com.datastax.driver.core.querybuilder.QueryBuilder.*;

@Singleton final class TreasureDAOImpl implements TreasureDAO {
  private final Cassandra cassandra;

  @Inject TreasureDAOImpl(Cassandra cassandra) {
    this.cassandra = cassandra;
  }

  @Nonnull @CheckReturnValue @Override public List<Treasure> findByName(String name) {
    checkNotNull(name, "name");
    checkArgument(!name.isEmpty(), "name must be not empty.");
    return find(select().from(KEYSPACE_WOOF, TABLE_TREASURE).where(eq(COMMON_COLUMN_NAME, name)));
  }

  @Nonnull @CheckReturnValue @Override public List<Treasure> findAll() {
    // TODO 未来数量很多的情况下，请进行分页处理。
    return find(select().from(KEYSPACE_WOOF, TABLE_TREASURE));
  }

  @Nonnull @CheckReturnValue @Override public Mapper<Treasure> getMapper() {
    return cassandra.mapper(Treasure.class);
  }
}
