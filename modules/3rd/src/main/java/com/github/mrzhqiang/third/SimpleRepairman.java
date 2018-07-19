package com.github.mrzhqiang.third;

import com.github.mrzhqiang.core.Cassandra;
import com.github.mrzhqiang.core.Redis;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * A simplest repairman.
 *
 * @author mrzhqiang
 */
@Singleton final class SimpleRepairman implements Repairman {
  private final Redis redis;
  private final Cassandra cassandra;

  @Inject
  SimpleRepairman(Redis redis, Cassandra cassandra) {
    this.redis = redis;
    this.cassandra = cassandra;
  }

  @Override public void repair() {
    redis.init();
    cassandra.init();
  }
}
