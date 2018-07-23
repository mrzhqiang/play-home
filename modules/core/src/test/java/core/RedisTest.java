package core;

import com.google.inject.Guice;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.BinaryJedis;

import static org.junit.Assert.*;

/**
 * @author mrzhqiang
 */
public final class RedisTest {
  private Redis redis;

  @Before
  public void setUp() {
    redis = Guice.createInjector().getInstance(Redis.class);
  }

  @After
  public void tearDown() {
    redis = null;
  }

  @Test
  public void getJedis() {
    assertEquals(redis.apply(BinaryJedis::ping), "PONG");
  }
}
