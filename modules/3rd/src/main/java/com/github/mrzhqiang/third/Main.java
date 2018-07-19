package com.github.mrzhqiang.third;

import com.google.inject.Guice;

/**
 * @author mrzhqiang
 */
public final class Main {
  public static void main(String[] args) {
    Repairman repairman = Guice.createInjector().getInstance(Repairman.class);
    repairman.repair();
    System.out.println("Now application will exit.");
    System.exit(0);
  }
}
