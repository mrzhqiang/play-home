package framework;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Objects;
import play.libs.Json;

/**
 * @author mrzhqiang
 */
public final class JsonList {
  private JsonList() {
  }

  public static <T> List<T> from(JsonNode jsonNode, Class<T> clazz) {
    Objects.requireNonNull(jsonNode, "jsonNode");
    try {
      ObjectMapper mapper = Json.mapper();
      JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, clazz);
      return mapper.readValue(jsonNode.toString(), javaType);
    } catch (Exception e) {
      throw new RuntimeException("Can not parse json.", e);
    }
  }
}
