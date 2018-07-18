package com.github.mrzhqiang.core.common;

/**
 * Redis constant.
 *
 * @author mrzhqiang
 */
public final class RedisConstant {
  private RedisConstant() {
  }

  public static final String ROOT_PATH = "core.redis";
  public static final String HOST = ROOT_PATH + ".host";
  public static final String PORT = ROOT_PATH + ".port";
  public static final String TIMEOUT = ROOT_PATH + ".timeout";
  public static final String PASSWORD = ROOT_PATH + ".password";
  public static final String DATABASE = ROOT_PATH + ".database";
}
