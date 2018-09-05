package core;

import com.google.inject.ImplementedBy;
import java.util.Optional;
import java.util.function.Function;
import redis.clients.jedis.Jedis;

/**
 * Redis 客户端。
 *
 * @author mrzhqiang
 */
@ImplementedBy(StandaloneRedis.class)
public interface Redis {
  /**
   * 数据初始化。
   */
  void init();

  /**
   * 通过 {@link Function} 操作 {@link Jedis} 执行某些操作，得到指定的对象。
   * <p>
   * 提示：此方法自动管理打开的资源，不需要再对 {@link Jedis} 进行关闭。
   * <p>
   * 注意：之所以返回 {@link Optional} 是因为 {@link Jedis} 操作有可能失误，程序通常不从底层中断。
   */
  <T> Optional<T> get(Function<Jedis, T> function);
}
