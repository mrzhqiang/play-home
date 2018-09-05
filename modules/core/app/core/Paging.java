package core;

import java.util.List;

/**
 * 分页接口。
 *
 * @author mrzhqiang
 */
public interface Paging<E extends EBeanModel> {
  /** 可查询的总数量。 */
  int total();

  /** 起始索引位置。 */
  int from();

  /** 从起始索引开始查询的数量。 */
  int size();

  /** 最终查询到的结果列表。 */
  List<E> datas();
}
