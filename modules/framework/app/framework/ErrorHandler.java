package framework;

import com.google.common.base.VerifyException;
import com.typesafe.config.Config;
import core.exception.ApplicationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import play.Environment;
import play.Logger;
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
  private static final Logger.ALogger logger = Logger.of("framework");

  private final Environment environment;

  @Inject
  public ErrorHandler(Config config, Environment environment, OptionalSourceMapper sourceMapper,
      Provider<Router> routes) {
    super(config, environment, sourceMapper, routes);
    this.environment = environment;
  }

  @Override public CompletionStage<Result> onClientError(Http.RequestHeader request, int statusCode,
      String message) {
    try {
      if (environment.isProd() && request.path().contains("/v1")) {
        return convertAs(ErrorResponse.clientError(statusCode, message.hashCode(), message));
      }
    } catch (Exception e) {
      return convertAs(ErrorResponse.unknownError(e.getMessage().hashCode()));
    }
    return super.onClientError(request, statusCode, message);
  }

  @Override protected CompletionStage<Result> onProdServerError(Http.RequestHeader request,
      UsefulException exception) {
    Throwable cause = exception.cause;
    if (cause instanceof VerifyException) {
      return onClientError(request, Http.Status.BAD_REQUEST, cause.getMessage());
    }
    if (cause instanceof ApplicationException) {
      ApplicationException appException = (ApplicationException) cause;
      return onClientError(request, appException.statusCode(), appException.getMessage());
    }
    logger.error("internal server error.", exception);
    if (environment.isProd() && request.path().contains("/v1")) {
      return convertAs(ErrorResponse.serverError(cause));
    }
    return super.onProdServerError(request, exception);
  }

  private CompletionStage<Result> convertAs(ErrorResponse errorResponse) {
    return CompletableFuture.completedFuture(
        Results.status(errorResponse.getHttpStatus(), Json.toJson(errorResponse)));
  }
}
