package com.github.mrzhqiang.core.dao;

import com.github.mrzhqiang.core.Redis;
import com.github.mrzhqiang.core.entity.Treasure;
import com.google.inject.Guice;
import com.google.inject.Injector;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static com.github.mrzhqiang.core.common.CassandraConstant.*;

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
    dao = injector.getInstance(TreasureDAO.class);
    redis = injector.getInstance(Redis.class);
    Long lastId = redis.getJedis().hincrBy(KEYSPACE_WOOF, TREASURE_ID, 1);

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
    redis.getJedis().hdel(KEYSPACE_WOOF, TREASURE_ID);
    dao.delete(data);
    redis = null;
    dao = null;
    data = null;
  }

  @Test
  public void findByName() {
    String currentId = redis.getJedis().hget(KEYSPACE_WOOF, TREASURE_ID);
    Optional<Treasure> optionalTreasure = dao.findByName("test" + currentId);
    assertTrue(optionalTreasure.isPresent());
    assertEquals(data, optionalTreasure.get());
  }

  @Test
  public void findALL() {
    List<Treasure> treasureList = dao.findALL();
    assertEquals(1, treasureList.size());
  }
}