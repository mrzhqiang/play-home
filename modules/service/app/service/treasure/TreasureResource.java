package service.treasure;

import com.google.common.base.MoreObjects;
import java.util.Objects;

/**
 * 宝藏资源。
 * <p>
 * 用于 Form 表单数据绑定；或以 JSON 形式传递的 Body 参数。
 *
 * @author mrzhqiang
 */
public final class TreasureResource {
  private String id;
  private String name;
  private String description;
  private String link;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getLink() {
    return link;
  }

  public void setLink(String link) {
    this.link = link;
  }

  @Override public int hashCode() {
    return Objects.hash(name);
  }

  @Override public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (!(obj instanceof TreasureResource)) {
      return false;
    }

    TreasureResource other = (TreasureResource) obj;
    return Objects.equals(this.name, other.name);
  }

  @Override public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("id", id)
        .add("name", name)
        .add("description", description)
        .add("link", link)
        .toString();
  }
}
