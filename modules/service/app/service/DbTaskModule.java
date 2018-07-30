package service;

import com.google.inject.AbstractModule;

/**
 * 数据库任务模块，提供给 Play 应用启动时，执行一次初始化操作。
 *
 * @author mrzhqiang
 */
public final class DbTaskModule extends AbstractModule {
  @Override protected void configure() {
    bind(DbTask.class).asEagerSingleton();
  }
}
