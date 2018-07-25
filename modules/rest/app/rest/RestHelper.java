package rest;

import com.palominolabs.http.url.UrlBuilder;
import java.nio.charset.CharacterCodingException;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import play.mvc.Http;

/**
 * Rest 帮手。
 *
 * @author mrzhqiang
 */
public final class RestHelper {
  private RestHelper() {
  }

  /**
   * 根据相对路径片段生成超链接。
   */
  @Nonnull
  @CheckReturnValue
  public static String href(String... segments) {
    Http.Request request = Http.Context.current().request();
    final String[] hostPort = request.host().split(":");
    final String scheme = request.secure() ? "https" : "http";
    String host = hostPort[0];
    int port = (hostPort.length == 2) ? Integer.parseInt(hostPort[1]) : -1;
    try {
      return UrlBuilder.forHost(scheme, host, port)
          .pathSegments(segments)
          .toUrlString();
    } catch (CharacterCodingException e) {
      throw new IllegalStateException(e);
    }
  }
}
