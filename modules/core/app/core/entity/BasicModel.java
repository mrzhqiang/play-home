package core.entity;

import com.google.common.base.MoreObjects;
import io.ebean.Model;
import io.ebean.annotation.WhenCreated;
import io.ebean.annotation.WhenModified;
import java.time.Instant;
import java.util.Objects;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

/**
 * 实体的基础模型。
 *
 * @author mrzhqiang
 */
@MappedSuperclass
public abstract class BasicModel extends Model {
  @Id
  public Long id;
  @Version
  public Long version;
  @WhenCreated
  public Instant created;
  @WhenModified
  public Instant modified;

  abstract public BasicModel check();

  MoreObjects.ToStringHelper toStringHelper() {
    return MoreObjects.toStringHelper(this)
        .add("id", id)
        .add("version", version)
        .add("created", created)
        .add("modified", modified);
  }

  @Override public int hashCode() {
    return Objects.hash(id);
  }

  @Override public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (!(obj instanceof BasicModel)) {
      return false;
    }

    BasicModel other = (BasicModel) obj;
    return Objects.equals(id, other.id);
  }
}
