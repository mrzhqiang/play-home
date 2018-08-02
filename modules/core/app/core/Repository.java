package core;

import java.util.List;
import java.util.Optional;
import javax.annotation.Nonnull;

/**
 * 实体仓库。
 * <p>
 * 定义数据库的 CURD 操作。
 * <p>
 * 此接口应保持兼容性（无论是 CQL 还是 SQL）。
 *
 * @author qiang.zhang
 */
public interface Repository<I, T> {
  Optional<T> create(T entity);

  Optional<T> update(T entity);

  Optional<T> get(I primaryKey);

  Optional<T> delete(I primaryKey);

  @Nonnull List<T> list();
}
