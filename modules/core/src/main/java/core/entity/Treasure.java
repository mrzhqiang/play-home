package core.entity;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;
import com.google.common.base.MoreObjects;
import java.util.Date;
import java.util.Objects;

import static core.common.CassandraConstant.*;

/**
 * Home page display list of treasure.
 *
 * @author mrzhqiang
 */
@Table(keyspace = KEYSPACE_WOOF, name = TABLE_TREASURE)
public final class Treasure {
  @PartitionKey
  @Column(name = TREASURE_ID)
  public Long id;
  @Column(name = COMMON_COLUMN_NAME)
  public String name;
  @Column(name = COMMON_COLUMN_DESCRIPTION)
  public String description;
  @Column(name = COMMON_COLUMN_LINK)
  public String link;
  @Column(name = COMMON_COLUMN_CREATED)
  public Date created;
  @Column(name = COMMON_COLUMN_UPDATED)
  public Date updated;

  @Override public int hashCode() {
    return Objects.hashCode(id);
  }

  @Override public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (!(obj instanceof Treasure)) {
      return false;
    }

    Treasure other = (Treasure) obj;
    return Objects.equals(id, other.id);
  }

  @Override public String toString() {
    return MoreObjects.toStringHelper(this)
        .add(TREASURE_ID, id)
        .add(COMMON_COLUMN_NAME, name)
        .toString();
  }
}
