package core.common;

import com.datastax.driver.core.DataType;
import com.datastax.driver.core.Statement;
import com.google.common.collect.ImmutableMap;

import static com.datastax.driver.core.schemabuilder.SchemaBuilder.*;

/**
 * Cassandra constant.
 *
 * @author mrzhqiang
 */
public final class CassandraConstant {
  private CassandraConstant() {
  }

  public static final String COMMON_COLUMN_NAME = "name";
  public static final String COMMON_COLUMN_LINK = "link";
  public static final String COMMON_COLUMN_DESCRIPTION = "description";
  public static final String COMMON_COLUMN_CREATED = "created";
  public static final String COMMON_COLUMN_UPDATED = "updated";

  public static final String KEYSPACE_WOOF = "woof";
  public static final Statement CREATE_WOOF =
      createKeyspace(KEYSPACE_WOOF)
          .ifNotExists()
          .with()
          .replication(ImmutableMap.of("class", "SimpleStrategy", "replication_factor", "1"));

  public static final String TABLE_TREASURE = "treasures";
  public static final String TREASURE_ID = "treasure_id";
  public static final Statement CREATE_TREASURE =
      createTable(KEYSPACE_WOOF, TABLE_TREASURE)
          .ifNotExists()
          .addPartitionKey(TREASURE_ID, DataType.bigint())
          .addColumn(COMMON_COLUMN_NAME, DataType.text())
          .addColumn(COMMON_COLUMN_LINK, DataType.text())
          .addColumn(COMMON_COLUMN_DESCRIPTION, DataType.text())
          .addColumn(COMMON_COLUMN_CREATED, DataType.timestamp())
          .addColumn(COMMON_COLUMN_UPDATED, DataType.timestamp());
  private static final String INDEX_TREASURE_NAME = "index_treasure_name";
  public static final Statement CREATE_TREASURE_NAME_INDEX =
      createIndex(INDEX_TREASURE_NAME)
          .ifNotExists()
          .onTable(KEYSPACE_WOOF, TABLE_TREASURE)
          .andColumn(COMMON_COLUMN_NAME);
  // TODO: 2018/7/18 其他的建表语句
}