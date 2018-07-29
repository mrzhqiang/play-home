package core.dao;

import com.datastax.driver.core.querybuilder.QueryBuilder;
import core.BasicDAO;
import core.Cassandra;
import core.entity.Treasure;
import java.util.List;
import java.util.Optional;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;

import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;
import static core.common.CassandraConstant.*;
import static com.google.common.base.Preconditions.*;

@Singleton final class TreasureDAOImpl implements TreasureDAO {
  private final BasicDAO<Treasure> basicDAO;

  @Inject TreasureDAOImpl(Cassandra cassandra) {
    this.basicDAO = CassandraDAO.treasure(cassandra);
  }

  @Nonnull @CheckReturnValue @Override public List<Treasure> findByName(String name) {
    checkNotNull(name, "name");
    checkArgument(!name.isEmpty(), "name must be not empty.");
    return execute(
        QueryBuilder.select()
            .from(KEYSPACE_WOOF, TABLE_TREASURE)
            .where(eq(COMMON_COLUMN_NAME, name))
            .getQueryString());
  }

  @Nonnull @CheckReturnValue @Override public List<Treasure> findAll() {
    return execute(
        QueryBuilder.select()
            .from(KEYSPACE_WOOF, TABLE_TREASURE)
            .getQueryString());
  }

  @Override public boolean add(Treasure entity) {
    return basicDAO.add(entity);
  }

  @Override public boolean remove(Treasure entity) {
    return basicDAO.remove(entity);
  }

  @Override public boolean remove(Object... objects) {
    return basicDAO.remove(objects);
  }

  @Override public boolean update(Treasure entity, Object... objects) {
    return basicDAO.update(entity, objects);
  }

  @Override public Optional<Treasure> find(Object... objects) {
    return basicDAO.find(objects);
  }

  @Nonnull @Override public List<Treasure> execute(String query) {
    return basicDAO.execute(query);
  }

  @Nonnull @Override public Treasure check(Treasure entity) {
    return basicDAO.check(entity);
  }

  @Nonnull @Override public Treasure merge(Treasure oldEntity, Treasure newEntity) {
    return basicDAO.merge(oldEntity, newEntity);
  }
}
