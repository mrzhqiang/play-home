package core.dao;

import core.Redis;
import core.entity.Treasure;
import com.google.inject.Guice;
import com.google.inject.Injector;
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
    Long lastId = redis.apply(resource -> resource.hincrBy(KEYSPACE_WOOF, TREASURE_ID, 1));
    dao = injector.getInstance(TreasureDAO.class);
    data = Treasure.of(lastId, "test" + lastId,
        "The treasure entity is test data that should delete.", "http://randall.top");
    dao.add(data);
  }

  @After
  public void tearDown() {
    Long id = redis.apply(resource -> resource.hdel(KEYSPACE_WOOF, TREASURE_ID));
    dao.remove(id);
    redis = null;
    dao = null;
    data = null;
  }

  @Test
  public void findByName() {
    String currentId = redis.apply(resource -> resource.hget(KEYSPACE_WOOF, TREASURE_ID));
    List<Treasure> optionalTreasure = dao.findByName("test" + currentId);
    assertEquals(data, optionalTreasure.get(0));
  }

  @Test
  public void findALL() {
    List<Treasure> treasureList = dao.findAll();
    assertNotNull(treasureList);
  }
}