package core.entity;

import com.google.common.base.Verify;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import io.ebean.annotation.Index;
import io.ebean.annotation.Length;
import java.util.Objects;
import java.util.UUID;
import javax.annotation.Nonnull;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import util.NameHelper;

/**
 * 客户端。
 * <p>
 * 表示如果是移动端，则必须经过客户端权限验证；Web 端没有要求。
 *
 * @author mrzhqiang
 */
@Entity
@Table(name = "clients")
public final class Client extends EBeanModel {
  private static final String REGEX_API_KEY = "[a-z0-9-]";

  public static final String COL_NAME = "name";
  public static final String COL_API_KEY = "apikey";

  @Index(name = "index_client_name")
  @Column(name = COL_NAME, unique = true, nullable = false)
  @Length(24)
  private String name;
  @Column(name = COL_API_KEY, unique = true, nullable = false)
  private UUID apikey;

  @Nonnull
  public String getName() {
    return name;
  }

  public void setName(@Nonnull String name) {
    boolean b = NameHelper.checkName(name, 2, 24);
    Verify.verify(b, "invalid name: %s", name);
    this.name = name;
  }

  public void setApikey(@Nonnull UUID apikey) {
    boolean b = NameHelper.checkRegexAndLength(REGEX_API_KEY, apikey.toString(), 36, 36);
    Verify.verify(b, "invalid apikey: %s", apikey);
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
