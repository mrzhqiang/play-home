package rest.v1.treasure;

import com.google.common.base.MoreObjects;
import core.entity.Treasure;
import java.net.URL;
import java.util.Date;
import play.data.validation.ValidationError;
import util.LinkHelper;

import static play.data.validation.Constraints.*;

/**
 * 宝藏资源。
 * <p>
 * 也可以用于表单填写。
 *
 * @author mrzhqiang
 */
@Validate
public final class TreasureResource implements Validatable<ValidationError> {
  private String id;
  @Required(message = "宝藏名称必填")
  @MinLength(message = "名称至少 2 个字符", value = 2)
  @MaxLength(message = "名称最多 12 个字符", value = 12)
  private String name;
  @Required(message = "宝藏链接必填")
  @Pattern(message = "超链接验证失败", value = LinkHelper.SIMPLE_REGEX)
  private String link;
  @MaxLength(message = "简介最多 120 个字符", value = 120)
  private String description;
  private String href;
  private Date timestamp;

  static TreasureResource of(Treasure treasure) {
    TreasureResource resource = new TreasureResource();
    resource.id = String.valueOf(treasure.id);
    resource.name = treasure.name;
    resource.link = treasure.link;
    resource.description = treasure.description;
    resource.href = "/v1/treasures/" + resource.id;
    resource.timestamp = Date.from(treasure.modified);
    return resource;
  }

  Treasure toTreasure() {
    Treasure treasure = new Treasure();
    treasure.name = name;
    treasure.link = link;
    treasure.description = description;
    return treasure;
  }

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

  public String getLink() {
    return link;
  }

  public void setLink(String link) {
    this.link = link;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getHref() {
    return href;
  }

  public void setHref(String href) {
    this.href = href;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }

  @Override public ValidationError validate() {
    try {
      new URL(link);
    } catch (Exception e) {
      return new ValidationError("link", "未能识别的超链接");
    }
    return null;
  }

  @Override public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("id", id)
        .add("name", name)
        .add("description", description)
        .add("link", link)
        .add("href", href)
        .toString();
  }
}
