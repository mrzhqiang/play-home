package framework;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import play.libs.Json;

/**
 * Json 工具类。
 * <p>
 * 对 Play framework 框架 {@link Json} 类的扩展，比如 List、Set 和 Map 等数据接口的序列化。
 *
 * @author mrzhqiang
 */
public final class JsonUtil {
  private JsonUtil() {
  }

  /**
   * 通过 {@link JsonNode} 和指定数据类的 Class 对象，得到数据列表。
   */
  public static <T> List<T> fromList(JsonNode jsonNode, Class<T> clazz) {
    Objects.requireNonNull(jsonNode, "jsonNode");
    try {
      ObjectMapper mapper = Json.mapper();
      JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, clazz);
      return mapper.readValue(jsonNode.toString(), javaType);
    } catch (Exception e) {
      throw new RuntimeException("Can not parse json.", e);
    }
  }

  public static <K, V> Map<K, V> fromMap(JsonNode jsonNode, Class<K> key, Class<V> value) {

  }
}
