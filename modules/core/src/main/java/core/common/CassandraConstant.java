package core.common;

import com.datastax.driver.core.DataType;
import com.datastax.driver.core.Statement;
import com.google.common.collect.ImmutableMap;

import static com.datastax.driver.core.schemabuilder.SchemaBuilder.*;

/**
 * Cassandra 常量.
 *
 * @author mrzhqiang
 */
public final class CassandraConstant {
  private CassandraConstant() {
  }

  /**
   * 公共字段：name，表示名称。
   */
  public static final String COMMON_COLUMN_NAME = "name";
  /**
   * 公共字段：link，表示链接。
   */
  public static final String COMMON_COLUMN_LINK = "link";
  /**
   * 公共字段：description，表示简介。
   */
  public static final String COMMON_COLUMN_DESCRIPTION = "description";
  /**
   * 公共字段：created，表示创建时间。
   */
  public static final String COMMON_COLUMN_CREATED = "created";
  /**
   * 公共字段：updated，表示更新时间。
   */
  public static final String COMMON_COLUMN_UPDATED = "updated";

  /**
   * Woof 密钥空间。
   * <p>
   * 一般以工程名字命名。在同一工程下，有且仅有一个数据库密钥空间。
   */
  public static final String KEYSPACE_WOOF = "woof";
  /**
   * Woof 密钥空间的创建语句。
   * <p>
   * 只有当不存在时，才会真正执行创建。一般用于检查数据结构是否正常。
   */
  public static final Statement CREATE_WOOF =
      createKeyspace(KEYSPACE_WOOF)
          .ifNotExists()
          .with()
          .replication(ImmutableMap.of("class", "SimpleStrategy", "replication_factor", "1"));
  /**
   * 宝藏表。
   * <p>
   * 用于主页显示可跳转的宝藏列表。
   */
  public static final String TABLE_TREASURE = "treasures";
  /**
   * 宝藏 ID。
   * <p>
   * 使用 Redis 自动递增的 long 型正整数，为了可读性而进行的一项尝试。
   */
  public static final String TREASURE_ID = "treasure_id";
  /**
   * 宝藏表的创建语句。
   */
  public static final Statement CREATE_TREASURE =
      createTable(KEYSPACE_WOOF, TABLE_TREASURE)
          .ifNotExists()
          .addPartitionKey(TREASURE_ID, DataType.bigint())
          .addColumn(COMMON_COLUMN_NAME, DataType.text())
          .addColumn(COMMON_COLUMN_LINK, DataType.text())
          .addColumn(COMMON_COLUMN_DESCRIPTION, DataType.text())
          .addColumn(COMMON_COLUMN_CREATED, DataType.timestamp())
          .addColumn(COMMON_COLUMN_UPDATED, DataType.timestamp());
  /**
   * 宝藏表中 name 字段索引的创建语句。
   */
  public static final Statement CREATE_TREASURE_NAME_INDEX =
      createIndex("index_treasure_name")
          .ifNotExists()
          .onTable(KEYSPACE_WOOF, TABLE_TREASURE)
          .andColumn(COMMON_COLUMN_NAME);
  // TODO: 2018/7/18 其他的建表语句
}
