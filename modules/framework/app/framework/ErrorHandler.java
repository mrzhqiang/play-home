package framework;

import com.typesafe.config.Config;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import play.Environment;
import play.api.OptionalSourceMapper;
import play.api.UsefulException;
import play.api.routing.Router;
import play.http.DefaultHttpErrorHandler;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;

/**
 * 错误处理程序。
 * <p>
 * 参考：<a href="https://www.playframework.com/documentation/2.6.x/JavaErrorHandling">官方文档。</a>
 *
 * @author mrzhqiang
 */
@Singleton
public final class ErrorHandler extends DefaultHttpErrorHandler {
  private final Environment environment;
  private final String version;

  @Inject
  public ErrorHandler(Config config,
      Environment environment,
      OptionalSourceMapper sourceMapper,
      Provider<Router> routes) {
    super(config, environment, sourceMapper, routes);
    this.environment = environment;
    this.version = config.getString("framework.support.version");
  }

  @Override public CompletionStage<Result> onClientError(Http.RequestHeader request, int statusCode,
      String message) {
    try {
      // 如果是线上环境的移动端请求
      if (environment.isProd() && request.uri().startsWith(version)) {
        return convertAs(ErrorResponse.clientError(statusCode, message.hashCode(), message));
      }
      // TODO 自定义客户端错误页面
      return super.onClientError(request, statusCode, message);
    } catch (Exception e) {
      return convertAs(ErrorResponse.unknownError(e.getMessage().hashCode()));
    }
  }

  @Override protected CompletionStage<Result> onProdServerError(Http.RequestHeader request,
      UsefulException exception) {
    Throwable cause = exception.cause;
    if (cause instanceof ApplicationException) {
      ApplicationException appException = (ApplicationException) cause;
      return onClientError(request, appException.statusCode(), appException.getMessage());
    }
    if (environment.isProd() && request.uri().startsWith(version)) {
      return convertAs(ErrorResponse.serverError(cause));
    }
    // TODO 自定义服务器错误页面
    return super.onProdServerError(request, exception);
  }

  private CompletionStage<Result> convertAs(ErrorResponse errorResponse) {
    return CompletableFuture.completedFuture(
        Results.status(errorResponse.getHttpStatus(), Json.toJson(errorResponse)));
  }
}
