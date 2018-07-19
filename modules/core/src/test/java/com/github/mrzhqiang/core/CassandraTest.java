package com.github.mrzhqiang.core;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.google.inject.Guice;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static com.github.mrzhqiang.core.common.CassandraConstant.*;

/**
 * @author mrzhqiang
 */
public final class CassandraTest {
  private Cassandra cassandra;
  private Config config;

  @Before
  public void setUp() {
    config = ConfigFactory.load();
    cassandra = Guice.createInjector().getInstance(Cassandra.class);
  }

  @After
  public void tearDown() {
    cassandra = null;
    config = null;
  }

  @Test
  public void getMappingManager() {
    Session session = cassandra.getMappingManager().getSession();
    ResultSet rs = session.execute(QUERY_RELEASE_VERSION);
    String releaseVersion = config.getString(ROOT_PATH + "." + RELEASE_VERSION);
    assertEquals(rs.one().getString(RELEASE_VERSION), releaseVersion);
  }
}
