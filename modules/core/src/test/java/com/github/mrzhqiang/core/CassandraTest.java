package com.github.mrzhqiang.core;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.github.mrzhqiang.core.Cassandra;
import com.google.inject.Guice;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author mrzhqiang
 */
public final class CassandraTest {
  private Cassandra cassandra;
  private Config config;

  @Before
  public void setUp() {
    cassandra = Guice.createInjector().getInstance(Cassandra.class);
    config = ConfigFactory.load();
  }

  @After
  public void tearDown() {
    cassandra = null;
    config = null;
  }

  @Test
  public void getMappingManager() {
    Session session = cassandra.getMappingManager().getSession();
    ResultSet rs = session.execute(Cassandra.QUERY_RELEASE_VERSION);
    String releaseVersion = config.getString(Cassandra.ROOT_PATH + "." + Cassandra.RELEASE_VERSION);
    assertEquals(rs.one().getString(Cassandra.RELEASE_VERSION), releaseVersion);
  }
}
