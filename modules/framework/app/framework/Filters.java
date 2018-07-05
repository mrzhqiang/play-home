package framework;

import framework.filters.AccessLogFilter;
import framework.filters.VersionFilter;
import javax.inject.Inject;
import javax.inject.Singleton;
import play.http.DefaultHttpFilters;

/**
 * 自定义过滤器。
 * <p>
 * 参考：<a href="https://www.playframework.com/documentation/2.6.x/JavaHttpFilters">Filter 文档。</a>
 *
 * @author mrzhqiang
 */
@Singleton
public final class Filters extends DefaultHttpFilters {

  @Inject
  public Filters(VersionFilter versionFilter, AccessLogFilter accessLogFilter) {
    super(versionFilter, accessLogFilter);
  }
}
