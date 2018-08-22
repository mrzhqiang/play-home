package core;

import com.google.common.base.MoreObjects;
import io.ebean.Model;
import io.ebean.annotation.WhenCreated;
import io.ebean.annotation.WhenModified;
import java.time.Instant;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

/**
 * EBean 基础模型。
 *
 * @author mrzhqiang
 */
@MappedSuperclass
public abstract class EBeanModel extends Model {
  @Id
  public Long id;
  @Version
  public Long version;
  @WhenCreated
  public Instant created;
  @WhenModified
  public Instant modified;
  @Column(columnDefinition = "介绍，说明。")
  public String description;

  /**
   * 自检方法。
   * <p>
   * 用来检查必备字段是否有效，一般用于创建数据。
   */
  public boolean checkSelf() {
    Objects.requireNonNull(created, "created");
    Objects.requireNonNull(modified, "modified");
    return true;
  }

  protected MoreObjects.ToStringHelper toStringHelper() {
    return MoreObjects.toStringHelper(this)
        .add("id", id)
        .add("version", version)
        .add("created", created)
        .add("modified", modified)
        .add("description", description);
  }

  @Override public int hashCode() {
    return Objects.hash(id, version, created, modified, description);
  }

  @Override public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (!(obj instanceof EBeanModel)) {
      return false;
    }

    EBeanModel other = (EBeanModel) obj;
    return Objects.equals(id, other.id)
        && Objects.equals(version, other.version)
        && Objects.equals(created, other.created)
        && Objects.equals(modified, other.modified)
        && Objects.equals(description, other.description);
  }
}
