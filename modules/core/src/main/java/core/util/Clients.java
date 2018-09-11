package core.util;

import core.entity.Client;
import java.util.UUID;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

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
  public static boolean checkName(@Nullable String value) {
    return value != null && !value.isEmpty() && value.length() <= 24;
  }

  /**
   * 检查秘钥。
   */
  public static boolean checkApiKey(@Nullable UUID value) {
    return value != null;
  }

  /**
   * 客户端。
   */
  @Nonnull
  public static Client of(@Nonnull String name) {
    Client client = new Client();
    client.name = name;
    client.apikey = UUID.randomUUID();
    return client;
  }
}
