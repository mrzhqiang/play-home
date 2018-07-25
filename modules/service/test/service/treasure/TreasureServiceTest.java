package service.treasure;

import core.entity.Treasure;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import play.test.WithApplication;

import static org.junit.Assert.*;

/**
 * @author mrzhqiang
 */
public class TreasureServiceTest extends WithApplication {
  private TreasureService service;
  private Treasure treasure;

  @Before
  public void setUp() {
    service = instanceOf(TreasureService.class);
    treasure = new Treasure();
    treasure.id = UUID.randomUUID();
    treasure.name = "testService";
    treasure.description = "Just a test data from service.";
    treasure.link = "http://randall.top";

    try {
      Treasure treasureData = service.create(treasure).toCompletableFuture().get();
      treasureData.id = treasure.id;
      assertEquals(treasure, treasureData);
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }
  }

  @After
  public void tearDown() {
    try {
      Treasure treasureData = service.delete(this.treasure.id).toCompletableFuture().get();
      assertEquals(this.treasure, treasureData);
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }
    treasure = null;
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
      Treasure treasureData = service.get(treasure.id).toCompletableFuture().get();
      assertEquals(treasure, treasureData);
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void get1() {
    try {
      List<Treasure> treasureList =
          service.get(treasure.name).toCompletableFuture().get().collect(Collectors.toList());
      assertTrue(treasureList.contains(treasure));
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void update() {
    treasure.description = "update change here.";
    try {
      Treasure treasureData = service.update(treasure.id, treasure).toCompletableFuture().get();
      assertEquals(treasure, treasureData);
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }
  }
}
