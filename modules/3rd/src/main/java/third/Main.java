package third;

import com.google.inject.Guice;
import play.Logger;

/**
 * @author mrzhqiang
 */
public final class Main {
  private static final Logger.ALogger logger = Logger.of("third");

  public static void main(String[] args) {
    Repairman repairman = Guice.createInjector().getInstance(Repairman.class);
    logger.info("Now repairman will start.");
    repairman.repair();
    logger.info("Repairman finish.");
    System.exit(0);
  }
}
