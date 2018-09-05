package core.entity;

import com.google.common.base.Preconditions;
import core.EBeanModel;
import io.ebean.annotation.Index;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 客户端。
 * <p>
 * 包含：客户端名字和对应的请求秘钥。
 * <p>
 * 实际上这是一个白名单表，可以防止接口被一些脚本工具调用。
 * <p>
 * 当然也不可能完全防范，一旦发现，可以通过切换秘钥，使脚本失效。
 *
 * @author mrzhqiang
 */
@Entity
@Table(name = "clients")
public final class Client extends EBeanModel {
  public static final String NAME = "name";
  public static final String API_KEY = "apikey";

  @Index(name = "index_client_name")
  @Column(name = NAME, unique = true, nullable = false, length = 24,
      columnDefinition = "客户端名字，唯一，非空，最长 24 个字符。")
  public String name;
  @Column(name = API_KEY, nullable = false,
      columnDefinition = "请求秘钥，非空，UUID 类型。")
  public UUID apikey = UUID.randomUUID();

  @Override public boolean checkSelf() {
    Preconditions.checkNotNull(name);
    Preconditions.checkNotNull(apikey);
    Preconditions.checkState(!name.isEmpty() && name.length() <= 24);
    return super.checkSelf();
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
