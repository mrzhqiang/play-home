package core;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import java.util.Optional;
import java.util.function.Function;
import javax.inject.Singleton;
import play.Logger;
import redis.clients.jedis.BinaryJedis;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.exceptions.JedisException;

/**
 * 单机版 Redis 客户端。
 *
 * @author mrzhqiang
 */
@Singleton final class StandaloneRedis implements Redis {
  private static final Logger.ALogger logger = Logger.of("core");

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
      host = config.hasPath(HOST) ? config.getString(HOST) : host;
      port = config.hasPath(PORT) ? config.getInt(PORT) : port;
      timeout = config.hasPath(TIMEOUT) ? config.getInt(TIMEOUT) : timeout;
      password = config.hasPathOrNull(PASSWORD) && !config.getIsNull(PASSWORD)
          ? config.getString(PASSWORD) : null;
      database = config.hasPath(DATABASE) ? config.getInt(DATABASE) : database;
    }

    try {
      jedisPool = new JedisPool(new JedisPoolConfig(), host, port, timeout, password, database);
      logger.info("Jedis pool create successful.");
    } catch (Exception e) {
      String message = "Jedis pool create failed.";
      logger.error(message, e);
      throw new RuntimeException(message, e);
    }
  }

  @Override public void init() {
    Optional<String> ping = get(BinaryJedis::ping);
    ping.ifPresent(s -> logger.info("Redis connect status: {}", "PONG".equals(s)));
  }

  @Override public <T> Optional<T> get(Function<Jedis, T> function) {
    try (Jedis resource = jedisPool.getResource()) {
      return Optional.ofNullable(function.apply(resource));
    } catch (JedisConnectionException e) {
      String message = "Connection to redis failed.";
      logger.error(message, e);
      throw new RuntimeException(message, e);
    } catch (JedisException e) {
      String message = "Get instance of Jedis error.";
      logger.error(message, e);
    }
    return Optional.empty();
  }
}
