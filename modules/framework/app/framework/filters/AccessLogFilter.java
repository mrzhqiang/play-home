package framework.filters;

import akka.stream.Materializer;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;
import javax.inject.Inject;
import javax.inject.Singleton;
import play.Logger;
import play.mvc.Filter;
import play.mvc.Http;
import play.mvc.Result;

/**
 * 访问日志过滤器。
 * <p>
 * 参考：https://www.playframework.com/documentation/2.6.x/JavaHttpFilters
 *
 * @author mrzhqiang
 */
@Singleton
public final class AccessLogFilter extends Filter {
  private final Logger.ALogger logger = Logger.of("access");
  private static final String MESSAGE = "method={} uri={} status={} elapsed={}ms";

  @Inject
  public AccessLogFilter(Materializer mat) {
    super(mat);
  }

  @Override
  public CompletionStage<Result> apply(
      Function<Http.RequestHeader, CompletionStage<Result>> next,
      Http.RequestHeader req) {
    long startTime = System.currentTimeMillis();
    return next.apply(req).thenApply(result -> {
      long endTime = System.currentTimeMillis();
      long requestTime = endTime - startTime;

      logger.info(MESSAGE, req.method(), req.uri(), result.status(), requestTime);
      return result.withHeader("Request-Time", String.valueOf(requestTime));
    });
  }
}
