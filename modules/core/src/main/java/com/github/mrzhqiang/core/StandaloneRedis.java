package com.github.mrzhqiang.core;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import javax.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.exceptions.JedisException;

import static com.github.mrzhqiang.core.common.RedisConstant.*;

/**
 * @author mrzhqiang
 */
@Singleton final class StandaloneRedis implements Redis {
  private static final Logger logger = LoggerFactory.getLogger("core");

  private final JedisPool jedisPool;

  StandaloneRedis() {
    String host = Protocol.DEFAULT_HOST;
    int port = Protocol.DEFAULT_PORT;
    int timeout = Protocol.DEFAULT_TIMEOUT;
    String password = null;
    int database = Protocol.DEFAULT_DATABASE;

    Config config = ConfigFactory.load();
    if (config.hasPath(ROOT_PATH)) {
      host = config.getString(HOST);
      port = config.getInt(PORT);
      timeout = config.getInt(TIMEOUT);
      String temp = config.getString(PASSWORD);
      password = temp.isEmpty() ? null : temp;
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

  @Override public void init() {
    try (Jedis jedis = getJedis()) {
      logger.info("Redis connect status: {}", "PONG".equals(jedis.ping()));
    }
  }

  @Override public Jedis getJedis() {
    try {
      return jedisPool.getResource();
    } catch (JedisConnectionException e) {
      String message = "Connection to jedis failed.";
      logger.error(message, e);
      throw new RuntimeException(message, e);
    } catch (JedisException e) {
      String message = "Get instance of jedis error.";
      logger.error(message, e);
      throw new RuntimeException(message, e);
    }
  }
}
