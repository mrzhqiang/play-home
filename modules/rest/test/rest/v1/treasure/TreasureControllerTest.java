package rest.v1.treasure;

import org.junit.Test;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;
import play.test.WithApplication;

import static org.junit.Assert.*;

/**
 * @author mrzhqiang
 */
public class TreasureControllerTest extends WithApplication {

  @Override protected Application provideApplication() {
    return new GuiceApplicationBuilder().build();
  }

  @Test
  public void create() {
    TreasureResource resource = new TreasureResource();
    resource.setName("testRest");
    resource.setDescription("Just a test data from rest.");
    resource.setLink("http://randall.top");

    Http.RequestBuilder builder = new Http.RequestBuilder()
        .method(Helpers.POST)
        .uri("http://localhost:9000/v1/treasures")
        .bodyJson(Json.toJson(resource));

    Result result = Helpers.route(app, builder);
    assertEquals(Http.Status.CREATED, result.status());
  }

  @Test
  public void show() {
  }

  @Test
  public void list() {
  }

  @Test
  public void search() {
  }

  @Test
  public void update() {
  }

  @Test
  public void delete() {
  }
}