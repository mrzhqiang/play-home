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
public final class TreasureData {
  @Required(message = "宝藏名称必填！")
  @MinLength(message = "名称长度至少 2 个字符", value = 2)
  @MaxLength(message = "名称长度最多 12 个字符", value = 12)
  private String name;
  private String description;
  private String link;
  private String href;
}
