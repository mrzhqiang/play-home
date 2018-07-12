package com.github.mrzhqiang.core.database;

import com.google.inject.ImplementedBy;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Protocol;

/**
 * Redis client interface.
 *
 * @author mrzhqiang
 */
@ImplementedBy(SingleRedis.class)
public interface Redis {
  String ROOT_PATH = "core.redis";
  String HOST = ROOT_PATH + ".host";
  String PORT = ROOT_PATH + ".port";
  String TIMEOUT = ROOT_PATH + ".timeout";
  String PASSWORD = ROOT_PATH + ".password";
  String DATABASE = ROOT_PATH + ".database";

  String DEFAULT_HOST = Protocol.DEFAULT_HOST;
  int DEFAULT_PORT = Protocol.DEFAULT_PORT;
  int DEFAULT_TIMEOUT = Protocol.DEFAULT_TIMEOUT;
  String DEFAULT_PASSWORD = null;
  int DEFAULT_DATABASE = Protocol.DEFAULT_DATABASE;

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
  Jedis getJedis();
}
