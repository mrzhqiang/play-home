package service;

import com.google.inject.AbstractModule;

/**
 * @author mrzhqiang
 */
public final class DbTaskModule extends AbstractModule {
  @Override protected void configure() {
    bind(DbTask.class).asEagerSingleton();
  }
}
