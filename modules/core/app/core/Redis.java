package core;

import com.google.inject.ImplementedBy;
import java.util.Optional;
import java.util.function.Function;
import javax.annotation.WillClose;
import redis.clients.jedis.Jedis;

/**
 * Redis 客户端。
 *
 * @author mrzhqiang
 */
@ImplementedBy(StandaloneRedis.class)
public interface Redis {
  /**
   * 数据初始化，同时也可以认为是一种内部纠错。
   */
  void init();

  /**
   * 通过 {@link Function} 操作 {@link Jedis} 执行某些操作，得到指定的对象。
   * <p>
   * 注意：之所以返回 {@link Optional} 是因为 {@link Jedis} 操作有可能失误，程序通常不从底层中断。
   */
  <T> Optional<T> get(@WillClose Function<Jedis, T> function);
}
