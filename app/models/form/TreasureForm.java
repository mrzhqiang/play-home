package models.form;

import java.net.URL;
import play.data.validation.ValidationError;
import util.UrlHelper;

import static play.data.validation.Constraints.*;

/**
 * 宝藏表单。
 *
 * @author mrzhqiang
 */
@Validate
public final class TreasureForm implements Validatable<ValidationError> {
  @Required(message = "宝藏名称必填")
  @MinLength(message = "名称至少 2 个字符", value = 2)
  @MaxLength(message = "名称最多 8 个字符", value = 8)
  private String name;
  @Required(message = "宝藏简介必填")
  @MaxLength(message = "介绍最多 120 个字符", value = 120)
  private String description;
  @Required(message = "宝藏链接必填")
  @Pattern(message = "超链接验证失败", value = UrlHelper.SIMPLE_REGEX)
  private String link;

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

  @Override public ValidationError validate() {
    try {
      new URL(link);
    } catch (Exception e) {
      return new ValidationError("link", "未能识别的超链接");
    }
    return null;
  }
}
