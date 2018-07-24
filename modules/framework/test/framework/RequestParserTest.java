package framework;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.junit.Test;
import play.libs.Json;
import util.DateHelper;

import static org.junit.Assert.*;

/**
 * @author mrzhqiang
 */
public class RequestParserTest {
  @Test
  public void asBool() {
    boolean b = RequestParser.asBool("1");
    assertFalse(b);
    b = RequestParser.asBool("TRUE");
    assertTrue(b);
    b = RequestParser.asBool("true");
    assertTrue(b);
  }

  @Test
  public void asInt() {
    int i = RequestParser.asInt("1");
    assertEquals(1, i);
    i = RequestParser.asInt("001");
    assertEquals(1, i);
    try {
      RequestParser.asInt(String.valueOf(Long.MAX_VALUE));
      assertNotEquals(1, i);
    } catch (ApplicationException ignore) {
    }
    try {
      RequestParser.asInt("ASSSS..a.");
      assertNotEquals(1, i);
    } catch (ApplicationException ignore) {
    }
  }

  @Test
  public void asLong() {
    long l = RequestParser.asLong("1");
    assertEquals(1, l);
    l = RequestParser.asLong("001");
    assertEquals(1, l);
    l = RequestParser.asLong("10000000000");
    assertEquals(10000000000L, l);
    try {
      RequestParser.asLong("ASSSS..a.");
      assertNotEquals(10000000000L, l);
    } catch (ApplicationException ignore) {
    }
  }

  @Test
  public void asFloat() {
    float f = RequestParser.asFloat("1.0");
    assertEquals(1.0F, f, 0.1F);
    f = RequestParser.asFloat("001.0");
    assertEquals(1.0F, f, 0.1F);
    f = RequestParser.asFloat("0.000001F");
    assertEquals(0.000001F, f, 0.0000001F);
    try {
      RequestParser.asFloat("ASSSS..a.");
      assertNotEquals(0.000001F, f, 0.0000001F);
    } catch (ApplicationException ignore) {
    }
  }

  @Test
  public void asDouble() {
    double d = RequestParser.asDouble("1.0");
    assertEquals(1.0, d, 0.1);
    d = RequestParser.asDouble("001.0");
    assertEquals(1.0, d, 0.1);
    d = RequestParser.asDouble("0.000001D");
    assertEquals(0.000001, d, 0.0000001);
    try {
      RequestParser.asDouble("ASSSS..a.");
      assertNotEquals(0.000001, d, 0.0000001);
    } catch (ApplicationException ignore) {
    }
  }

  @Test
  public void asUUID() {
    UUID uuid = UUID.randomUUID();
    UUID u = RequestParser.asUUID(uuid.toString());
    assertEquals(uuid, u);
    try {
      UUID asUUID = RequestParser.asUUID("ASSSS..a.");
      assertEquals(uuid, asUUID);
    } catch (ApplicationException ignore) {
    }
  }

  @Test
  public void asDate() {
    // 只需要秒级别的日期
    Date value = Date.from(Instant.now().truncatedTo(ChronoUnit.SECONDS));
    String resource = DateHelper.formatNormal(value);
    Date d = RequestParser.asDate(resource);
    assertEquals(value, d);
    try {
      d = RequestParser.asDate("asdasdasw");
      assertEquals(value, d);
    } catch (ApplicationException ignore) {
    }
  }

  @Test
  public void fromJson() {
    ErrorResponse response = ErrorResponse.unknownError(-1);
    JsonNode jsonNode = Json.toJson(response);
    ErrorResponse errorResponse = RequestParser.fromJson(jsonNode, ErrorResponse.class);
    assertEquals(response, errorResponse);
    try {
      errorResponse = RequestParser.fromJson(Json.toJson("asdasdasw"), ErrorResponse.class);
      assertNotEquals(response, errorResponse);
    } catch (ApplicationException ignore) {
    }
  }

  @Test
  public void fromListJson() {
    List<ErrorResponse> responses =
        Lists.newArrayList(ErrorResponse.unknownError(-1), ErrorResponse.unknownError(-2));
    JsonNode jsonNode = Json.toJson(responses);
    List<ErrorResponse> responseList = RequestParser.fromListJson(jsonNode, ErrorResponse.class);
    assertEquals(responses, responseList);
    try {
      responseList = RequestParser.fromListJson(Json.toJson("asdasdasw"), ErrorResponse.class);
      assertNotEquals(responses, responseList);
    } catch (ApplicationException ignore) {
    }
  }

  @Test
  public void fromSetJson() {
    Set<ErrorResponse> responses =
        Sets.newHashSet(ErrorResponse.unknownError(-1), ErrorResponse.unknownError(-2));
    JsonNode jsonNode = Json.toJson(responses);
    Set<ErrorResponse> responseSet = RequestParser.fromSetJson(jsonNode, ErrorResponse.class);
    assertEquals(responses, responseSet);
    try {
      responseSet = RequestParser.fromSetJson(Json.toJson("asdasdasw"), ErrorResponse.class);
      assertNotEquals(responses, responseSet);
    } catch (ApplicationException ignore) {
    }
  }

  @Test
  public void fromCollectionJson() {
    List<ErrorResponse> responses =
        Lists.newArrayList(ErrorResponse.unknownError(-1), ErrorResponse.unknownError(-2));
    JsonNode jsonNode = Json.toJson(responses);
    List<ErrorResponse> responseList =
        RequestParser.fromCollectionJson(jsonNode.toString(), List.class, ErrorResponse.class);
    assertEquals(responses, responseList);
    try {
      responseList =
          RequestParser.fromCollectionJson(Json.toJson("asdasdasw").toString(), List.class,
              ErrorResponse.class);
      assertNotEquals(responses, responseList);
    } catch (ApplicationException ignore) {
    }
  }

  @Test
  public void fromMapJson() {
    Map<String, ErrorResponse> responseMap = Maps.newHashMap();
    responseMap.put("1", ErrorResponse.unknownError(1));
    JsonNode jsonNode = Json.toJson(responseMap);
    Map<String, ErrorResponse> errorResponseMap =
        RequestParser.fromMapJson(jsonNode, String.class, ErrorResponse.class);
    assertEquals(responseMap, errorResponseMap);
    try {
      errorResponseMap =
          RequestParser.fromMapJson(Json.toJson("1212121"), String.class, ErrorResponse.class);
      assertNotEquals(responseMap, errorResponseMap);
    } catch (ApplicationException ignore) {
    }
  }

  @Test
  public void fromMapJson1() {
    Map<String, ErrorResponse> responseMap = Maps.newLinkedHashMap();
    responseMap.put("1", ErrorResponse.unknownError(1));
    JsonNode jsonNode = Json.toJson(responseMap);
    Map<String, ErrorResponse> errorResponseMap =
        RequestParser.fromMapJson(jsonNode.toString(), Map.class, String.class,
            ErrorResponse.class);
    assertEquals(responseMap, errorResponseMap);
    try {
      errorResponseMap =
          RequestParser.fromMapJson("121212", Map.class, String.class, ErrorResponse.class);
      assertNotEquals(responseMap, errorResponseMap);
    } catch (ApplicationException ignore) {
    }
  }
}
