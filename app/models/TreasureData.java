package models;

import static play.data.validation.Constraints.*;

/**
 * 宝藏数据。
 * <p>
 * 一般用于主页数据列表显示，以及 Form 表单填写。
 *
 * @author qiang.zhang
 */
@Validate
public final class TreasureData implements Validatable<String> {
  @Required(message = "宝藏名称必填！")
  @MinLength(message = "名称至少 2 个字符", value = 2)
  @MaxLength(message = "名称最多 8 个字符", value = 8)
  private String name;
  @MaxLength(message = "介绍最多 120 个字符", value = 120)
  private String description;
  @Required(message = "宝藏链接必填！")
  private String link;
  private String href;

  @Override public String validate() {
    return null;
  }
}
