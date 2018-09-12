package framework;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapType;
import com.google.common.base.Verify;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import javax.annotation.Nonnull;
import play.libs.Json;
import util.DateHelper;

import static com.google.common.base.Preconditions.checkNotNull;
import static core.exception.ApplicationException.badRequest;

/**
 * 简单解析器。
 * <p>
 * 只包含对 基本类型、JsonNode 以及 Json 字符串的解析。
 *
 * @author mrzhqiang
 */
public final class SimpleParser {
  private SimpleParser() {
  }

  public static boolean asBool(String resource) {
    try {
      return Boolean.parseBoolean(resource);
    } catch (Exception e) {
      throw badRequest("parse boolean type failed: " + e.getMessage());
    }
  }

  public static int asInt(String resource) {
    try {
      return Integer.parseInt(resource);
    } catch (Exception e) {
      throw badRequest("parse int type failed: " + e.getMessage());
    }
  }

  public static long asLong(String resource) {
    try {
      return Long.parseLong(resource);
    } catch (Exception e) {
      throw badRequest("parse long type failed: " + e.getMessage());
    }
  }

  public static float asFloat(String resource) {
    try {
      return Float.parseFloat(resource);
    } catch (Exception e) {
      throw badRequest("parse float type failed: " + e.getMessage());
    }
  }

  public static double asDouble(String resource) {
    try {
      return Double.parseDouble(resource);
    } catch (Exception e) {
      throw badRequest("parse double type failed: " + e.getMessage());
    }
  }

  @Nonnull
  public static UUID asUUID(String resource) {
    try {
      return UUID.fromString(checkNotNull(resource));
    } catch (Exception e) {
      throw badRequest("parse UUID type failed: " + e.getMessage());
    }
  }

  @Nonnull
  public static Date asDate(String resource) {
    try {
      Date date = DateHelper.parseNormal(resource);
      if (date != null) {
        return date;
      }
    } catch (Exception e) {
      throw badRequest("parse Date type failed: " + e.getMessage());
    }
    throw badRequest("can not parse to java.util.Date");
  }

  /**
   * 通过 Json 字符串源和指定类型，得到此类型的实例。
   * <p>
   * 本质上就是 {@link Json#fromJson(JsonNode, Class)}。
   */
  @Nonnull
  public static <T> T fromJson(JsonNode jsonNode, Class<T> clazz) {
    Verify.verify(jsonNode != null && jsonNode.isObject(), "json node null or not be object");
    try {
      T t = Json.fromJson(jsonNode, clazz);
      if (t != null) {
        return t;
      }
    } catch (Exception e) {
      throw badRequest("json node parse failed: " + e.getMessage());
    }
    throw badRequest("json node can not parse");
  }

  /**
   * 通过 {@link JsonNode} 和元素类型，得到此元素的 {@link ArrayList} 实例。
   * <p>
   * 本质上就是 {@link ObjectMapper#readValue(String, JavaType)}
   */
  @Nonnull
  public static <E> List<E> fromListJson(JsonNode jsonNode, Class<E> clazz) {
    Verify.verify(jsonNode != null && jsonNode.isArray(), "json node null or not be array.");
    return fromCollectionJson(jsonNode.toString(), ArrayList.class, clazz);
  }

  /**
   * 通过 {@link JsonNode} 和元素类型，得到此元素的 {@link HashSet} 实例。
   * <p>
   * 本质上就是 {@link ObjectMapper#readValue(String, JavaType)}
   */
  @Nonnull
  public static <E> Set<E> fromSetJson(JsonNode jsonNode, Class<E> clazz) {
    Verify.verify(jsonNode != null && jsonNode.isArray(), "json node null or not be array.");
    return fromCollectionJson(jsonNode.toString(), HashSet.class, clazz);
  }

  /**
   * 通过 Json 字符串源，指定类型以及元素类型，得到此元素的扩展的 {@link Collection} 实例。
   * <p>
   * 本质上就是 {@link ObjectMapper#readValue(String, JavaType)}
   */
  @Nonnull
  public static <T> T fromCollectionJson(String resource,
      Class<? extends Collection> collectionClass,
      Class<?> clazz) {
    try {
      ObjectMapper mapper = Json.mapper();
      CollectionType collectionType =
          mapper.getTypeFactory().constructCollectionType(collectionClass, clazz);
      T t = mapper.readValue(resource, collectionType);
      if (t != null) {
        return t;
      }
    } catch (Exception e) {
      throw badRequest("json node parse failed: " + e.getMessage());
    }
    throw badRequest("json node can not parse to collection");
  }

  /**
   * 通过 {@link JsonNode} 类型，得到字符串的 {@link HashMap} 实例。
   */
  @Nonnull
  public static Map<String, String> fromMapString(JsonNode jsonNode) {
    Verify.verify(jsonNode != null && jsonNode.isObject(), "json node null or not be map.");
    return fromMapJson(jsonNode, String.class, String.class);
  }

  /**
   * 通过 {@link JsonNode} 和 Key、Value 类型，得到相应的 {@link HashMap} 实例。
   * <p>
   * 本质上就是 {@link ObjectMapper#readValue(String, JavaType)}
   */
  @Nonnull
  public static <K, V> Map<K, V> fromMapJson(JsonNode jsonNode, Class<K> keyClass,
      Class<V> valueClass) {
    Verify.verify(jsonNode != null && jsonNode.isObject(), "json node null or not be map.");
    return fromMapJson(jsonNode.toString(), HashMap.class, keyClass, valueClass);
  }

  /**
   * 通过 Json 字符串源，指定类型以及 Key、Value 类型，得到相应的扩展的 {@link Map} 实例。
   * <p>
   * 本质上就是 {@link ObjectMapper#readValue(String, JavaType)}
   */
  @Nonnull
  public static <K, V> Map<K, V> fromMapJson(String resource, Class<? extends Map> mapClass,
      Class<K> keyClass, Class<V> valueClass) {
    try {
      ObjectMapper mapper = Json.mapper();
      MapType mapType =
          mapper.getTypeFactory().constructMapType(mapClass, keyClass, valueClass);
      Map<K, V> map = mapper.readValue(resource, mapType);
      if (map != null) {
        return map;
      }
    } catch (Exception e) {
      throw badRequest("json node parse failed: " + e.getMessage());
    }
    throw badRequest("json node can not parse to map");
  }
}
