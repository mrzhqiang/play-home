package core;

import com.google.inject.ImplementedBy;
import java.util.function.Function;
import redis.clients.jedis.Jedis;

/**
 * Redis 客户端接口。
 *
 * @author mrzhqiang
 */
@ImplementedBy(StandaloneRedis.class)
public interface Redis {
  /** 应该在启动应用之前，通过其他程序调用一次，以保证数据库正常初始化。 */
  void init();

  /** 供给 Jedis 实例，以防止忘记关闭资源，浪费服务器性能。 */
  <R> R apply(Function<Jedis, R> function);
}
