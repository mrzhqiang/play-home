package core.entity;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;
import com.google.common.base.MoreObjects;
import core.common.Entity;
import java.util.Objects;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;

import static com.google.common.base.Preconditions.checkState;
import static com.google.common.base.Strings.isNullOrEmpty;
import static core.common.CassandraConstant.*;
import static java.util.Objects.isNull;

/**
 * 宝藏。
 *
 * @author mrzhqiang
 */
@Table(keyspace = KEYSPACE_WOOF, name = TABLE_TREASURE)
public final class Treasure implements Entity<Treasure> {
  @PartitionKey
  @Column(name = TREASURE_ID)
  public final Long id;
  @Column(name = COMMON_COLUMN_NAME)
  public final String name;
  @Column(name = COMMON_COLUMN_DESCRIPTION)
  public final String description;
  @Column(name = COMMON_COLUMN_LINK)
  public final String link;
  @Column(name = COMMON_COLUMN_CREATED)
  public final Long created;
  @Column(name = COMMON_COLUMN_UPDATED)
  public final Long updated;

  @Nonnull
  @CheckReturnValue
  public static Treasure of(Long id, String name, String description, String link) {
    return new Treasure(id, name, description, link);
  }

  private Treasure(Long id, String name, String description, String link) {
    this(id, name, description, link, System.currentTimeMillis(), System.currentTimeMillis());
  }

  private Treasure(Long id, String name, String description, String link, Long created,
      Long updated) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.link = link;
    this.created = created;
    this.updated = updated;
  }

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

  @Nonnull @Override public Treasure check() {
    checkState(id != null && id > 0, TREASURE_ID);
    checkState(!isNullOrEmpty(name), COMMON_COLUMN_NAME);
    checkState(!isNullOrEmpty(description), COMMON_COLUMN_DESCRIPTION);
    checkState(!isNullOrEmpty(link), COMMON_COLUMN_LINK);
    checkState(!isNull(created), COMMON_COLUMN_CREATED);
    checkState(!isNull(updated), COMMON_COLUMN_UPDATED);
    return this;
  }

  @Nonnull @CheckReturnValue @Override public Treasure merge(Treasure valueEntity) {
    Objects.requireNonNull(valueEntity, "valueEntity");
    String name = isNullOrEmpty(valueEntity.name) ? this.name : valueEntity.name;
    String description =
        isNullOrEmpty(valueEntity.description) ? this.description : valueEntity.description;
    String link = isNullOrEmpty(valueEntity.link) ? this.link : valueEntity.link;
    return new Treasure(this.id, name, description, link, this.created, System.currentTimeMillis());
  }
}
