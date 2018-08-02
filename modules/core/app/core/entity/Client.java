package core.entity;

import com.google.common.base.Strings;
import core.BaseModel;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import static core.exception.ApplicationException.badRequest;

/**
 * 客户端。
 *
 * @author mrzhqiang
 */
@Entity
@Table(name = "clients")
public final class Client extends BaseModel {
  @Column(unique = true, nullable = false, length = 24, columnDefinition = "客户端名，唯一，非 null，最长 24 个字符。")
  public String name;
  @Column(nullable = false, columnDefinition = "接口秘钥，非 null，UUID 类型。")
  public UUID apikey;

  @Override public void check() {
    badRequest(checkName(), "Invalid name: " + name);
    badRequest(apikey != null, "Null apikey");
  }

  private boolean checkName() {
    return !Strings.isNullOrEmpty(name) && name.length() >= 2 && name.length() <= 24;
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
        .add("name", name)
        .add("apikey", apikey)
        .toString();
  }
}
