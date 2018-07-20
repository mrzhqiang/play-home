package core;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.google.inject.Guice;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static com.datastax.driver.core.querybuilder.QueryBuilder.select;
import static org.junit.Assert.*;

/**
 * @author mrzhqiang
 */
public final class CassandraTest {
  private static final String ROOT_PATH = "core.cassandra";
  private static final String CLUSTER_NAME = "cluster_name";
  private static final String RELEASE_VERSION = "release_version";

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
    ResultSet rs = session.execute(
        select(CLUSTER_NAME, RELEASE_VERSION)
            .from("system", "local"));
    String releaseVersion = config.getString(ROOT_PATH + "." + RELEASE_VERSION);
    assertEquals(rs.one().getString(RELEASE_VERSION), releaseVersion);
  }
}
