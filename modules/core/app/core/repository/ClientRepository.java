package core.repository;

import com.google.inject.ImplementedBy;
import core.Repository;
import core.entity.Client;
import java.util.Base64;
import java.util.Optional;

/**
 * 客户端仓库。
 *
 * @author qiang.zhang
 */
@ImplementedBy(EBeanClientRepository.class)
public interface ClientRepository extends Repository<Long, Client> {
  String BASIC = "Basic ";

  Optional<Client> verify(String username, String password);

  default Optional<Client> authenticate(String basicAuth) {
    return Optional.ofNullable(basicAuth)
        .filter(s -> s.startsWith(BASIC))
        .map(s -> new String(Base64.getDecoder().decode(s.replaceFirst(BASIC, ""))))
        .filter(s -> s.contains(":"))
        .map(s -> s.split(":", 2))
        .flatMap(strings -> verify(strings[0], strings[1]));
  }
}
