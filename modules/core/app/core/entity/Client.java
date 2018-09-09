package core.entity;

import com.google.common.base.Preconditions;
import core.util.Clients;
import io.ebean.annotation.Index;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 客户端。
 *
 * @author mrzhqiang
 */
@Entity
@Table(name = "clients")
public final class Client extends EBeanModel {
  private static final String BASE_INDEX = "index_client_";

  public static final String COL_NAME = "name";
  public static final String COL_API_KEY = "apikey";

  @Index(name = BASE_INDEX + COL_NAME)
  @Column(name = COL_NAME, unique = true, nullable = false, length = 24)
  public String name;
  @Column(name = COL_API_KEY, nullable = false)
  public UUID apikey;

  @Override public boolean checkSelf() {
    Preconditions.checkNotNull(name);
    Preconditions.checkState(Clients.checkName(name));
    Preconditions.checkNotNull(apikey);
    return true;
  }

  @Override public int hashCode() {
    return Objects.hash(super.hashCode(), name, apikey);
  }

  @Override public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (!(obj instanceof Client)) {
      return false;
    }

    Client other = (Client) obj;
    return super.equals(obj)
        && Objects.equals(name, other.name)
        && Objects.equals(apikey, other.apikey);
  }

  @Override public String toString() {
    return toStringHelper()
        .add("客户端", name)
        .add("秘钥", apikey)
        .toString();
  }
}
