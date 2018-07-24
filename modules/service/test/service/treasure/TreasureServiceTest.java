package service.treasure;

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
  private TreasureResource resource;

  @Before
  public void setUp() {
    service = instanceOf(TreasureService.class);
    resource = new TreasureResource();
    resource.setName("testName");
    resource.setDescription("Just a test data.");
    resource.setLink("http://randall.top");

    try {
      TreasureResource treasureResource = service.create(resource).toCompletableFuture().get();
      resource.setId(treasureResource.getId());
      assertEquals(resource, treasureResource);
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }
  }

  @After
  public void tearDown() {
    try {
      TreasureResource treasureResource =
          service.delete(UUID.fromString(resource.getId())).toCompletableFuture().get();
      assertEquals(resource, treasureResource);
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }
    resource = null;
    service = null;
  }

  @Test
  public void list() {
    try {
      List<TreasureResource> collect =
          service.list().toCompletableFuture().get().collect(Collectors.toList());
      assertNotNull(collect);
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void get() {
    try {
      TreasureResource treasureResource =
          service.get(UUID.fromString(resource.getId())).toCompletableFuture().get();
      assertEquals(resource, treasureResource);
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void get1() {
    try {
      List<TreasureResource> treasureResourceList =
          service.get(resource.getName()).toCompletableFuture().get().collect(Collectors.toList());
      assertTrue(treasureResourceList.contains(resource));
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void update() {
    resource.setDescription("update change here.");
    try {
      TreasureResource treasureResource =
          service.update(UUID.fromString(resource.getId()), this.resource)
              .toCompletableFuture()
              .get();
      assertEquals(resource, treasureResource);
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }
  }
}
