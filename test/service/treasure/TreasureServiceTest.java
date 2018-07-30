package service.treasure;

import core.entity.Treasure;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import play.test.WithApplication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author mrzhqiang
 */
public class TreasureServiceTest extends WithApplication {
  private TreasureService service;
  private Treasure treasureData;

  @Before
  public void setUp() {
    service = instanceOf(TreasureService.class);
    try {
      treasureData =
          service.create("testService", "http://randall.top", "Just a test data from service.")
              .toCompletableFuture()
              .get();
      assertNotNull(treasureData.id);
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }
  }

  @After
  public void tearDown() {
    try {
      Treasure treasure = service.delete(treasureData.id).toCompletableFuture().get();
      assertEquals(treasureData, treasure);
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }
    service = null;
  }

  @Test
  public void list() {
    try {
      List<Treasure> treasureList =
          service.list().toCompletableFuture().get().collect(Collectors.toList());
      assertNotNull(treasureList);
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void get() {
    try {
      Treasure treasure = service.get(treasureData.id).toCompletableFuture().get();
      assertEquals(treasureData, treasure);
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void get1() {
    try {
      Treasure treasure = service.get("testService").toCompletableFuture().get();
      assertEquals(treasureData, treasure);
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void update() {
    Treasure treasure = new Treasure();
    treasure.description = "change from update method.";
    try {
      Treasure newTreasure = service.update(treasureData.id, treasure).toCompletableFuture().get();
      assertEquals(treasureData, newTreasure);
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }
  }
}
