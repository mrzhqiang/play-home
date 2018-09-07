package core.repository;

import com.google.inject.ImplementedBy;
import core.entity.Client;
import java.util.Base64;
import java.util.Optional;
import javax.annotation.Nonnull;

/**
 * 客户端仓库。
 *
 * @author qiang.zhang
 */
@ImplementedBy(ClientEBeanRepository.class)
public interface ClientRepository extends Repository<Long, Client> {
  String BASIC = "Basic ";

  /**
   * 查询指定名字和秘钥是否存在。
   *
   * @param name 客户端名字。
   * @param apikey 秘钥。
   * @return 如果存在，说明是对的；不存在，那么验证失败。
   */
  @Nonnull Optional<Client> find(String name, String apikey);

  /**
   * 认证 HTTP 权限是否为客户端权限。
   *
   * @param basicAuth HTTP 头中的权限字段值。
   * @return 如果存在，说明有权限；不存在，那么权限认证失败。
   */
  @Nonnull default Optional<Client> authenticate(String basicAuth) {
    return Optional.ofNullable(basicAuth)
        .filter(s -> s.startsWith(BASIC))
        .map(s -> s.replaceFirst(BASIC, ""))
        .map(s -> new String(Base64.getDecoder().decode(s)))
        .filter(s -> s.contains(":"))
        .map(s -> s.split(":", 2))
        .flatMap(strings -> find(strings[0], strings[1]));
  }
}
