package models;

import rest.v1.treasure.TreasureResource;

/**
 * 宝藏数据。
 * <p>
 * 用于主页数据列表显示。
 *
 * @author qiang.zhang
 */
public final class TreasureData {
  public String name;
  public String link;
  public String description;
  public String href;

  public static TreasureData of(TreasureResource resource) {
    TreasureData data = new TreasureData();
    data.name = resource.name;
    data.link = resource.link;
    data.description = resource.description;
    data.href = resource.href;
    return data;
  }
}
