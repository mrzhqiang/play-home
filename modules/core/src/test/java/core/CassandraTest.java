package core;

import com.google.inject.Guice;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author mrzhqiang
 */
public final class CassandraTest {
  private Cassandra cassandra;

  @Before
  public void setUp() {
    cassandra = Guice.createInjector().getInstance(Cassandra.class);
  }

  @After
  public void tearDown() {
    cassandra = null;
  }

  @Test
  public void init() {
    cassandra.init();
  }
}
