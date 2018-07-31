package rest.v1.treasure;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.test.WithApplication;

import static junit.framework.TestCase.*;
import static play.mvc.Http.Status.*;
import static play.test.Helpers.*;

/**
 * @author mrzhqiang
 */
public class TreasureControllerTest extends WithApplication {
  private String id;

  @Before
  public void create() {
    TreasureResource resource = new TreasureResource(name, description, link, href);
    resource.setName("testRest");
    resource.setDescription("Just a test data from rest.");
    resource.setLink("http://randall.top");

    Http.RequestBuilder builder = fakeRequest()
        .method(POST)
        .uri("/v1/treasures")
        .bodyJson(Json.toJson(resource));

    Result result = route(app, builder);
    String jsonContent = contentAsString(result);
    TreasureResource treasureResource =
        Json.fromJson(Json.parse(jsonContent), TreasureResource.class);
    String href = treasureResource.getHref();
    id = href.substring(href.lastIndexOf("/") + 1);
    assertEquals(CREATED, result.status());
  }

  @Test
  public void show() {
    Http.RequestBuilder builder = fakeRequest()
        .uri("/v1/treasures/" + id);

    Result result = route(app, builder);
    assertEquals(OK, result.status());
  }

  @Test
  public void list() {
    Http.RequestBuilder builder = fakeRequest()
        .uri("/v1/treasures");

    Result result = route(app, builder);
    assertEquals(OK, result.status());
  }

  @Test
  public void update() {
    TreasureResource resource = new TreasureResource(name, description, link, href);
    resource.setDescription("Just a test data from rest update.");
    Http.RequestBuilder builder = fakeRequest()
        .method(PUT)
        .uri("/v1/treasures/" + id)
        .bodyJson(Json.toJson(resource));

    Result result = route(app, builder);
    assertEquals(OK, result.status());
  }

  @After
  public void delete() {
    Http.RequestBuilder builder = fakeRequest()
        .method(DELETE)
        .uri("/v1/treasures/" + id);

    Result result = route(app, builder);
    assertEquals(OK, result.status());
  }
}