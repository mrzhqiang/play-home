package core.dao;

import core.Redis;
import core.entity.Treasure;
import com.google.inject.Guice;
import com.google.inject.Injector;
import java.util.Date;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static core.common.CassandraConstant.*;

/**
 * @author mrzhqiang
 */
public class TreasureDAOTest {
  private Redis redis;
  private TreasureDAO dao;
  private Treasure data;

  @Before
  public void setUp() {
    Injector injector = Guice.createInjector();
    redis = injector.getInstance(Redis.class);
    Long lastId = redis.getRedis().hincrBy(KEYSPACE_WOOF, TREASURE_ID, 1);

    dao = injector.getInstance(TreasureDAO.class);
    data = new Treasure();
    data.id = lastId;
    data.name = "test" + lastId;
    data.link = "http://randall.top";
    data.description = "The treasure entity is test data that should delete.";
    data.updated = new Date();
    data.created = new Date();
    dao.add(data);
  }

  @After
  public void tearDown() {
    redis.getRedis().hdel(KEYSPACE_WOOF, TREASURE_ID);
    dao.remove(data);
    redis = null;
    dao = null;
    data = null;
  }

  @Test
  public void findByName() {
    String currentId = redis.getRedis().hget(KEYSPACE_WOOF, TREASURE_ID);
    List<Treasure> optionalTreasure = dao.findByName("test" + currentId);
    assertEquals(data, optionalTreasure.get(0));
  }

  @Test
  public void findALL() {
    List<Treasure> treasureList = dao.findAll();
    assertNotNull(treasureList);
  }
}