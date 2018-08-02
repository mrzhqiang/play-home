package core.repository;

import com.google.inject.ImplementedBy;
import core.Repository;
import core.entity.Client;
import core.internal.ClientEBeanRepository;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

/**
 * 客户端仓库。
 *
 * @author qiang.zhang
 */
@ImplementedBy(ClientEBeanRepository.class)
public interface ClientRepository extends Repository<Long, Client> {
  String BASIC = "Basic ";

  default Optional<String> authenticate(String basicAuth) {
    return Optional.ofNullable(basicAuth)
        .filter(s -> s.startsWith(BASIC))
        .map(s -> new String(Base64.getDecoder().decode(s.replaceFirst(BASIC, ""))))
        .filter(s -> s.contains(":"))
        .flatMap(s -> {
          String[] split = s.split(":", 2);
          return verify(split[0], split[1]);
        })
        .map(client -> client.name);
  }

  Optional<Client> verify(String username, String password);

  default UUID generateApikey() {
    return UUID.randomUUID();
  }
}
