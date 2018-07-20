package third;

import com.google.inject.Guice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author mrzhqiang
 */
public final class Main {
  private static final Logger logger = LoggerFactory.getLogger(Main.class);

  public static void main(String[] args) {
    Repairman repairman = Guice.createInjector().getInstance(Repairman.class);
    logger.info("Now repairman will start.");
    repairman.repair();
    logger.info("Repairman finish.");
    System.exit(0);
  }
}
