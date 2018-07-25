package service.treasure;

import com.typesafe.config.Config;
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
  private Config config;
  private TreasureService service;

  @Before
  public void setUp() {
    config = instanceOf(Config.class);
    service = instanceOf(TreasureService.class);
    Treasure treasure = new Treasure();
    treasure.id = UUID.fromString(config.getString("service.treasure.testId"));
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
      UUID id = UUID.fromString(config.getString("service.treasure.testId"));
      Treasure treasureData = service.delete(id).toCompletableFuture().get();
      assertEquals(id, treasureData.id);
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }
    service = null;
    config = null;
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
      UUID id = UUID.fromString(config.getString("service.treasure.testId"));
      Treasure treasureData = service.get(id).toCompletableFuture().get();
      assertEquals(id, treasureData.id);
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void get1() {
    try {
      List<Treasure> treasureList =
          service.get("testService").toCompletableFuture().get().collect(Collectors.toList());
      assertNotNull(treasureList);
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void update() {
    UUID id = UUID.fromString(config.getString("service.treasure.testId"));
    Treasure treasure = new Treasure();
    treasure.description = "change from update method.";
    try {
      Treasure treasureData = service.update(id, treasure).toCompletableFuture().get();
      assertEquals(id, treasureData.id);
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }
  }
}
