package third;

import com.google.inject.Guice;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author mrzhqiang
 */
public class RepairmanTest {
  private Repairman repairman;

  @Before
  public void setUp() {
    repairman = Guice.createInjector().getInstance(Repairman.class);
  }

  @After
  public void tearDown() {
    repairman = null;
  }

  @Test
  public void repair() {
    try {
      repairman.repair();
    } catch (Exception e) {
      assertNotNull(e);
      e.printStackTrace();
    }
  }
}
