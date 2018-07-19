package com.github.mrzhqiang.core;

import com.google.inject.ImplementedBy;
import redis.clients.jedis.Jedis;

/**
 * Redis client interface.
 *
 * @author mrzhqiang
 */
@ImplementedBy(StandaloneRedis.class)
public interface Redis {
  /**
   * You should call this method in [3rd] module if the woof application for the first time to run.
   */
  void init();

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
   *
   * <p>
   * Note: The method return Jedis is not cluster object, so it can not apply to multi cluster node.
   */
  Jedis getJedis();
}
