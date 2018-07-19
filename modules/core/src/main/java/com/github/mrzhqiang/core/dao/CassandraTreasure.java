package com.github.mrzhqiang.core.dao;

import com.datastax.driver.mapping.Mapper;
import com.github.mrzhqiang.core.Cassandra;
import com.github.mrzhqiang.core.entity.Treasure;
import java.util.List;
import java.util.Optional;
import javax.inject.Inject;
import javax.inject.Singleton;

import static com.datastax.driver.core.querybuilder.QueryBuilder.*;
import static com.github.mrzhqiang.core.common.CassandraConstant.*;

@Singleton final class CassandraTreasure implements TreasureDAO {
  private final Cassandra cassandra;

  @Inject CassandraTreasure(Cassandra cassandra) {
    this.cassandra = cassandra;
  }

  @Override public Mapper<Treasure> getMapper() {
    return cassandra.getMappingManager().mapper(Treasure.class);
  }

  @Override public Optional<Treasure> findByName(String name) {
    return find(select().from(KEYSPACE_WOOF, TABLE_TREASURE).where(eq(COMMON_COLUMN_NAME, name)));
  }

  @Override public List<Treasure> findALL() {
    return findAll(select().from(KEYSPACE_WOOF, TABLE_TREASURE));
  }
}
