package com.github.mrzhqiang.core;

import com.google.inject.ImplementedBy;
import redis.clients.jedis.Jedis;

/**
 * Redis client interface.
 *
 * @author mrzhqiang
 */
@ImplementedBy(SingleRedis.class)
public interface Redis {
  /**
   * In JDK 1.6 and lower:
   * <pre>
   *   Jedis jedis = null;
   *    try {
   *     jedis = redis.getJedis();
   *     // do something...
   *   } finally {
   *     if (jedis != null) {
   *       jedis.close();
   *     }
   *   }
   * </pre>
   *
   * <p>
   * In JDK 1.7 and higher:
   * <pre>
   *   try (Jedis jedis = redis.getJedis()) {
   *     // do something...
   *   }
   * </pre>
   */
  // TODO: 2018/7/18 考虑未来的集群集成，需要一个兼容的返回类型
  Jedis getJedis();
}
