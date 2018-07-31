package core.repository;

import java.util.List;
import java.util.Optional;
import javax.annotation.Nonnull;

/**
 * 实体仓库。
 * <p>
 * 定义基本的 CURD 功能，以及对实体的检查、合并操作。
 *
 * @author qiang.zhang
 */
interface Repository<I, T> {
  @Nonnull List<T> list();

  @Nonnull T create(T entity);

  Optional<T> delete(I primaryKey);

  Optional<T> update(I primaryKey, T entity);

  Optional<T> get(I primaryKey);
}
