package core;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import java.util.function.Function;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import javax.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.BinaryJedis;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.exceptions.JedisException;

/**
 * @author mrzhqiang
 */
@Singleton final class StandaloneRedis implements Redis {
  private static final Logger logger = LoggerFactory.getLogger("core");

  private static final String ROOT_PATH = "core.redis";
  private static final String HOST = ROOT_PATH + ".host";
  private static final String PORT = ROOT_PATH + ".port";
  private static final String TIMEOUT = ROOT_PATH + ".timeout";
  private static final String PASSWORD = ROOT_PATH + ".password";
  private static final String DATABASE = ROOT_PATH + ".database";

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
      logger.info("Jedis pool create successful.");
    } catch (Exception e) {
      String message = "Jedis pool create failed.";
      logger.error(message, e);
      throw new RuntimeException(message, e);
    }
  }

  @Override public void init() {
    String ping = apply(BinaryJedis::ping);
    logger.info("Redis connect status: {}", "PONG".equals(ping));
  }

  @Nonnull @CheckReturnValue @Override public <R> R apply(Function<Jedis, R> function) {
    try (Jedis resource = jedisPool.getResource()) {
      return function.apply(resource);
    } catch (JedisConnectionException e) {
      String message = "Connection to redis failed.";
      logger.error(message, e);
      throw new RuntimeException(message, e);
    } catch (JedisException e) {
      String message = "Get instance of Jedis error.";
      logger.error(message, e);
      throw new RuntimeException(message, e);
    }
  }
}
