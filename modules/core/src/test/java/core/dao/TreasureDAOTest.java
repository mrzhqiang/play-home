package core.dao;

import core.entity.Treasure;
import com.google.inject.Guice;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author mrzhqiang
 */
public class TreasureDAOTest {
  private TreasureDAO dao;
  private Treasure data;

  @Before
  public void setUp() {
    dao = Guice.createInjector().getInstance(TreasureDAO.class);
    data = new Treasure();
    data.name = "test";
    data.description = "The treasure entity is test data that should delete.";
    data.link = "http://randall.top";
    dao.add(data);
  }

  @After
  public void tearDown() {
    dao.remove(data);
    dao = null;
    data = null;
  }

  @Test
  public void findByName() {
    List<Treasure> optionalTreasure = dao.findByName("test");
    assertTrue(optionalTreasure.contains(data));
  }

  @Test
  public void findALL() {
    List<Treasure> treasureList = dao.findAll();
    assertNotNull(treasureList);
  }
}