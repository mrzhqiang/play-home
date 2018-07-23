package service.treasure;

import com.google.common.base.MoreObjects;
import core.entity.Treasure;
import play.mvc.Http;

/**
 * 宝藏资源。
 * <p>
 * 用于 Form 表单数据绑定；或以 JSON 形式传递的 Body 参数。
 *
 * @author mrzhqiang
 */
public final class TreasureResource {
  private String name;
  private String description;
  private String link;
  private String href;

  public static TreasureResource fromData(Treasure treasure) {
    TreasureResource resource = new TreasureResource();
    resource.name = treasure.name;
    resource.description = treasure.description;
    resource.link = treasure.link;
    return resource;
  }

  public Treasure toData(Long id) {
    return Treasure.of(id, name, description, link);
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

  public String getHref() {
    return href;
  }

  public void setHref(String href) {
    this.href = href;
  }

  @Override public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("name", name)
        .add("description", description)
        .add("link", link)
        .add("href", href)
        .toString();
  }
}
