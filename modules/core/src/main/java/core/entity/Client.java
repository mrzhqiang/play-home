package core.entity;

import com.google.common.base.Verify;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import io.ebean.annotation.Index;
import java.util.Objects;
import java.util.UUID;
import javax.annotation.Nonnull;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 客户端。
 * <p>
 * TODO 客户端 access log
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
  private String name;
  @Column(name = COL_API_KEY, nullable = false)
  private UUID apikey;

  @Nonnull
  public String getName() {
    return name;
  }

  public void setName(@Nonnull String name) {
    Verify.verify(!name.isEmpty() && name.length() <= 24,
        "invalid name: %s", name);
    this.name = name;
  }

  @Nonnull
  public UUID getApikey() {
    return apikey;
  }

  public void setApikey(@Nonnull UUID apikey) {
    this.apikey = apikey;
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
    return stringHelper()
        .add("客户端", name)
        .add("秘钥", apikey)
        .toString();
  }

  /**
   * 根据名字生成唯一的客户端。
   */
  @Nonnull
  @CanIgnoreReturnValue
  public static Client of(@Nonnull String name) {
    Client client = new Client();
    client.setName(name);
    client.setApikey(UUID.randomUUID());
    return client;
  }
}
