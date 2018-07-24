package core.entity;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;
import com.google.common.base.MoreObjects;
import core.common.Entity;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;

import static com.google.common.base.Preconditions.checkState;
import static com.google.common.base.Strings.isNullOrEmpty;
import static core.common.CassandraConstant.*;

/**
 * 宝藏。
 *
 * @author mrzhqiang
 */
@Table(keyspace = KEYSPACE_WOOF, name = TABLE_TREASURE)
public final class Treasure implements Entity<Treasure> {
  @PartitionKey
  @Column(name = TREASURE_ID)
  public UUID id;
  @Column(name = COMMON_COLUMN_NAME)
  public String name;
  @Column(name = COMMON_COLUMN_DESCRIPTION)
  public String description;
  @Column(name = COMMON_COLUMN_LINK)
  public String link;
  @Column(name = COMMON_COLUMN_CREATED)
  public Date created = new Date();
  @Column(name = COMMON_COLUMN_UPDATED)
  public Date updated = new Date();

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
        .add(COMMON_COLUMN_DESCRIPTION, description)
        .add(COMMON_COLUMN_LINK, link)
        .add(COMMON_COLUMN_CREATED, created)
        .add(COMMON_COLUMN_UPDATED, updated)
        .toString();
  }

  @Nonnull @Override public Treasure check() {
    checkState(id != null, TREASURE_ID);
    checkState(!isNullOrEmpty(name), COMMON_COLUMN_NAME);
    checkState(!isNullOrEmpty(description), COMMON_COLUMN_DESCRIPTION);
    checkState(!isNullOrEmpty(link), COMMON_COLUMN_LINK);
    checkState(created != null, COMMON_COLUMN_CREATED);
    checkState(updated != null, COMMON_COLUMN_UPDATED);
    return this;
  }

  @Nonnull @CheckReturnValue @Override public Treasure merge(Treasure valueEntity) {
    Objects.requireNonNull(valueEntity, "valueEntity");
    name = isNullOrEmpty(valueEntity.name) ? name : valueEntity.name;
    description = isNullOrEmpty(valueEntity.description) ? description : valueEntity.description;
    link = isNullOrEmpty(valueEntity.link) ? link : valueEntity.link;
    this.updated = new Date();
    return this;
  }
}
