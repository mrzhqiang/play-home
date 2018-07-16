package com.github.mrzhqiang.core.database;

import com.google.inject.Singleton;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import play.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.exceptions.JedisException;

/**
 * Redis of single host。
 *
 * @author mrzhqiang
 */
@Singleton final class SingleRedis implements Redis {
  private static final Logger.ALogger logger = Logger.of("core");

  private final JedisPool jedisPool;

  public SingleRedis() {
    String host = DEFAULT_HOST;
    int port = DEFAULT_PORT;
    int timeout = DEFAULT_TIMEOUT;
    String password = DEFAULT_PASSWORD;
    int database = DEFAULT_DATABASE;

    Config config = ConfigFactory.load();
    if (config.hasPath(ROOT_PATH)) {
      host = config.getString(HOST);
      port = config.getInt(PORT);
      timeout = config.getInt(TIMEOUT);
      String temp = config.getString(PASSWORD);
      password = temp.isEmpty() ? DEFAULT_PASSWORD : temp;
      database = config.getInt(DATABASE);
    }

    try {
      this.jedisPool =
          new JedisPool(new JedisPoolConfig(), host, port, timeout, password, database);
    } catch (Exception e) {
      String message = "Create jedis pool failed.";
      logger.error(message, e);
      throw new RuntimeException(message, e);
    }
  }

  @Override public Jedis getJedis() {
    try {
      return jedisPool.getResource();
    } catch (JedisConnectionException e) {
      throw new RuntimeException("Connection to jedis failed.", e);
    } catch (JedisException e) {
      throw new RuntimeException("Get instance of jedis error.", e);
    }
  }
}