package core.util;

import core.entity.Client;
import java.util.UUID;
import javax.annotation.Nonnull;

/**
 * 客户端工具。
 *
 * @author mrzhqiang
 */
public final class Clients {
  private Clients() {
    throw new AssertionError("No instance.");
  }

  /**
   * 检查名字。
   */
  public static boolean checkName(@Nonnull String value) {
    return !value.isEmpty() && value.length() <= 24;
  }

  @Nonnull
  public static Client of(@Nonnull String name) {
    Client client = new Client();
    client.name = name;
    client.apikey = UUID.randomUUID();
    return client;
  }
}
