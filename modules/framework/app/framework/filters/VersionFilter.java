package framework.filters;

import akka.stream.Materializer;
import com.typesafe.config.Config;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;
import javax.inject.Inject;
import javax.inject.Singleton;
import play.mvc.Filter;
import play.mvc.Http.RequestHeader;
import play.mvc.Result;

/**
 * 版本过滤器。
 * <p>
 * 参考：https://www.playframework.com/documentation/2.6.x/JavaHttpFilters
 *
 * @author mrzhqiang
 */
@Singleton
public final class VersionFilter extends Filter {
  private final String apiVersion;

  @Inject
  public VersionFilter(Materializer mat, Config conf) {
    super(mat);
    this.apiVersion = conf.getString("app.version");
  }

  @Override
  public CompletionStage<Result> apply(Function<RequestHeader, CompletionStage<Result>> next,
      RequestHeader req) {
    return next.apply(req).thenApplyAsync(result -> result.withHeader("X-Version", apiVersion));
  }
}
